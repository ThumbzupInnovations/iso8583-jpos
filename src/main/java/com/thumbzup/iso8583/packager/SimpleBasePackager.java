/*
 *     Copyright (C) 2016 Sean
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.thumbzup.iso8583.packager;

import org.jpos.iso.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Map;
import java.util.StringTokenizer;

@SuppressWarnings("unused")
public abstract class SimpleBasePackager implements ISOPackager {
    protected ISOFieldPackager[] fld;

    protected String realm = null;
    protected int headerLength = 0;

    public void setFieldPackager(ISOFieldPackager[] fld) {
        this.fld = fld;
    }

    protected boolean emitBitMap() {
        return (fld[1] instanceof ISOBitMapPackager);
    }

    protected int getFirstField() {
        if ((!(fld[0] instanceof ISOMsgFieldPackager)) && fld.length > 1)
            return (fld[1] instanceof ISOBitMapPackager) ? 2 : 1;
        return 0;
    }

    @Override
    public byte[] pack(ISOComponent m) throws ISOException {
        if (m.getComposite() != m)
            throw new ISOException("Can't call packager on non Composite");

        ISOComponent c;
        ArrayList<byte[]> v = new ArrayList<>(128);
        Map<?, ?> fields = m.getChildren();
        int len = 0;
        int first = getFirstField();

        c = (ISOComponent) fields.get(0);
        byte[] b;

        if (m instanceof ISOMsg && headerLength > 0) {
            byte[] h = ((ISOMsg) m).getHeader();
            if (h != null)
                len += h.length;
        }

        if (first > 0 && c != null) {
            b = fld[0].pack(c);
            len += b.length;
            v.add(b);
        }

        if (emitBitMap()) {
            // BITMAP (-1 in HashTable)
            c = (ISOComponent) fields.get(-1);
            b = getBitMapfieldPackager().pack(c);
            len += b.length;
            v.add(b);
        }

        // if Field 1 is a BitMap then we are packing an
        // ISO-8583 message so next field is fld#2.
        // else we are packing an ANSI X9.2 message, first field is 1
        int tmpMaxField = Math.min(m.getMaxField(), 128);

        for (int i = first; i <= tmpMaxField; i++) {
            if ((c = (ISOComponent) fields.get(i)) != null) {
                try {
                    ISOFieldPackager fp = fld[i];
                    if (fp == null)
                        throw new ISOException("null field " + i + " packager");
                    b = fp.pack(c);
                    len += b.length;
                    v.add(b);
                } catch (ISOException e) {
                    e.printStackTrace();
                    throw new ISOException("error packing field " + i, e);
                }
            }
        }

        if (m.getMaxField() > 128 && fld.length > 128) {
            for (int i = 1; i <= 64; i++) {
                if ((c = (ISOComponent) fields.get(i + 128)) != null) {
                    b = fld[i + 128].pack(c);
                    len += b.length;
                    v.add(b);
                }
            }
        }

        int k = 0;
        byte[] d = new byte[len];

        // if ISOMsg insert header
        if (m instanceof ISOMsg && headerLength > 0) {
            byte[] h = ((ISOMsg) m).getHeader();
            if (h != null) {
                System.arraycopy(h, 0, d, k, h.length);
                k += h.length;
            }
        }
        for (byte[] bb : v) {
            System.arraycopy(bb, 0, d, k, bb.length);
            k += bb.length;
        }
        return d;
    }

    @Override
    public int unpack(ISOComponent m, byte[] b) throws ISOException {
        int consumed = 0;

        try {
            if (m.getComposite() != m)
                throw new ISOException("Can't call packager on non Composite");

            // if ISOMsg and headerLength defined
            if (m instanceof ISOMsg /* && ((ISOMsg) m).getHeader()==null */ && headerLength > 0) {
                byte[] h = new byte[headerLength];
                System.arraycopy(b, 0, h, 0, headerLength);
                ((ISOMsg) m).setHeader(h);
                consumed += headerLength;
            }

            if (!(fld[0] == null) && !(fld[0] instanceof ISOBitMapPackager)) {
                ISOComponent mti = fld[0].createComponent(0);
                consumed += fld[0].unpack(mti, b, consumed);
                m.set(mti);
            }
            BitSet bmap = null;
            int maxField = fld.length;
            if (emitBitMap()) {
                ISOBitMap bitmap = new ISOBitMap(-1);
                consumed += getBitMapfieldPackager().unpack(bitmap, b, consumed);
                bmap = (BitSet) bitmap.getValue();
                m.set(bitmap);
                maxField = Math.min(maxField, bmap.size());
            }
            for (int i = getFirstField(); i < maxField; i++) {
                try {
                    if (bmap == null && fld[i] == null)
                        continue;
                    if (maxField > 128 && i == 65)
                        continue; // ignore extended bitmap

                    if (bmap == null || bmap.get(i)) {
                        if (fld[i] == null)
                            throw new ISOException("field packager '" + i + "' is null");

                        ISOComponent c = fld[i].createComponent(i);
                        consumed += fld[i].unpack(c, b, consumed);
                        m.set(c);
                    }
                } catch (ISOException e) {
                    m.dump(System.out,"");
                    e = new ISOException(String.format("%s (%s) unpacking field=%d, consumed=%d", e.getMessage(), e
                            .getNested().toString(), i, consumed));
                    throw e;
                }
            }
            return consumed;
        } catch (ISOException e) {
            throw e;
        } catch (Exception e) {
            throw new ISOException(e.getMessage() + " consumed=" + consumed);
        }
    }

    @Override
    public void unpack(ISOComponent m, InputStream in) throws IOException, ISOException {
        try {
            if (m.getComposite() != m)
                throw new ISOException("Can't call packager on non Composite");

            // if ISOMsg and headerLength defined
            if (m instanceof ISOMsg && ((ISOMsg) m).getHeader() == null && headerLength > 0) {
                byte[] h = new byte[headerLength];
                int count = in.read(h, 0, headerLength);
                if (count != headerLength) {
                    throw new ISOException("Invalid header");
                }
                ((ISOMsg) m).setHeader(h);
            }

            if (!(fld[0] instanceof ISOMsgFieldPackager) && !(fld[0] instanceof ISOBitMapPackager)) {
                ISOComponent mti = fld[0].createComponent(0);
                fld[0].unpack(mti, in);
                m.set(mti);
            }
            BitSet bmap = null;
            int maxField = fld.length;
            if (emitBitMap()) {
                ISOBitMap bitmap = new ISOBitMap(-1);
                getBitMapfieldPackager().unpack(bitmap, in);
                bmap = (BitSet) bitmap.getValue();
                m.set(bitmap);
                maxField = Math.min(maxField, bmap.size());
            }

            for (int i = getFirstField(); i < maxField; i++) {
                if (bmap == null && fld[i] == null)
                    continue;

                if (bmap == null || bmap.get(i)) {
                    if (fld[i] == null)
                        throw new ISOException("field packager '" + i + "' is null");

                    ISOComponent c = fld[i].createComponent(i);
                    fld[i].unpack(c, in);
                    m.set(c);
                }
            }
            if (bmap != null && bmap.get(65) && fld.length > 128 && fld[65] instanceof ISOBitMapPackager) {
                bmap = (BitSet) ((ISOComponent) m.getChildren().get(65)).getValue();
                for (int i = 1; i < 64; i++) {
                    if (bmap == null || bmap.get(i)) {
                        ISOComponent c = fld[i + 128].createComponent(i);
                        fld[i + 128].unpack(c, in);
                        m.set(c);
                    }
                }
            }
        } catch (ISOException | EOFException e) {
            throw e;
        } catch (Exception e) {
            throw new ISOException(e);
        }
    }

    @Override
    public String getFieldDescription(ISOComponent m, int fldNumber) {
        return fld[fldNumber].getDescription();
    }

    public ISOFieldPackager getFieldPackager(int fldNumber) {
        return fld != null && fldNumber < fld.length ? fld[fldNumber] : null;
    }

    public ISOFieldPackager getFieldPackager(String path) {
        StringTokenizer st = new StringTokenizer(path, ".");
        int fldno = Integer.parseInt(st.nextToken());
        return fld != null && fldno < fld.length ? fld[fldno] : null;
    }

    public void setFieldPackager(int fldNumber, ISOFieldPackager fieldPackager) {
        fld[fldNumber] = fieldPackager;
    }

    @Override
    public ISOMsg createISOMsg() {
        return new ISOMsg();
    }

    protected ISOFieldPackager getBitMapfieldPackager() {
        return fld[1];
    }

    public String getRealm() {
        return realm;
    }

    public int getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(int len) {
        headerLength = len;
    }

    @Override
    public String getDescription() {
        return getClass().getName();
    }
}
