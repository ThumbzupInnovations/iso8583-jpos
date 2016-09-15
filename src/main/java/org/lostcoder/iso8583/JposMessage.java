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

package org.lostcoder.iso8583;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOFieldPackager;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.BASE24Packager;
import org.lostcoder.iso8583.exception.Iso8583Exception;
import org.lostcoder.iso8583.packager.AS2805Packager;

/**
 * Created by Sean on 2016/09/15.
 */
public class JposMessage extends ISOMsg implements Iso8583Message {

    byte[] packCache = null;

    private boolean modified;

    public JposMessage(String messageType, AcquirerProtocol protocol) {
        super(messageType);
        setPackager(protocol);
    }

    private void setPackager(AcquirerProtocol protocol) {
        switch (protocol) {
            case BASE24:
                setPackager(new BASE24Packager());
                break;
            case AS2805:
                setPackager(new AS2805Packager());
                break;
        }
    }

    public JposMessage(byte[] isoData, AcquirerProtocol protocol) throws Iso8583Exception {
        setPackager(protocol);
        try {
            unpack(isoData);
        } catch (ISOException e) {
            throw new Iso8583Exception(e);
        }
        modified = true;
    }

    public void setField(int no, String value) throws Iso8583Exception {
        modified = true;
        try {
            super.set(no, value);
        } catch (ISOException e) {
            throw new Iso8583Exception(e);
        }
    }

    public void setField(int no, byte[] value) throws Iso8583Exception {
        modified = true;
        try {
            super.set(no, value);
        } catch (ISOException e) {
            throw new Iso8583Exception(e);
        }
    }

    @Override
    public byte[] encode() throws Iso8583Exception {
        if (packCache == null || modified) {
            try {
                packCache = super.pack();
                modified = false;
            } catch (ISOException e) {
                throw new Iso8583Exception(e);
            }
        }
        return packCache;
    }

    @Override
    public boolean isNetworkMessage() throws Iso8583Exception {
        return getMessageType().charAt(1) == '8';
    }

    @Override
    public Object getFieldValue(int no) throws Iso8583Exception {
        try {
            return getValue(no);
        } catch (ISOException e) {
            throw new Iso8583Exception(e);
        }
    }

    @Override
    public String getMessageType() throws Iso8583Exception {
        try {
            return getMTI();
        } catch (ISOException e) {
            throw new Iso8583Exception(e);
        }
    }

    @Override
    public String getFieldValueAsString(int no) {
        return getString(no);
    }

    @Override
    public byte[] getFieldValueAsEncoded(int no) throws Iso8583Exception {
        AS2805Packager as2805Packager = (AS2805Packager) getPackager();
        ISOFieldPackager fieldPackager = as2805Packager.getFieldPackager(no);
        try {
            return fieldPackager.pack(getComponent(no));
        } catch (ISOException e) {
            throw new Iso8583Exception(e);
        }
    }

    @Override
    public String getFieldDescription(int no) {
        return this.getPackager().getFieldDescription(getComponent(no), no);
    }

    @Override
    public byte[] getFieldValueAsBytes(int no) {
        return getBytes(no);
    }

    @Override
    public void setResponseMessageType() throws Iso8583Exception {
        try {
            setResponseMTI();
        } catch (ISOException e) {
            throw new Iso8583Exception(e);
        }
    }

    @Override
    public void setRetransmissionMessageType() throws Iso8583Exception {
        try {
            setRetransmissionMTI();
        } catch (ISOException e) {
            throw new Iso8583Exception(e);
        }
    }
}
