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

package com.thumbzup.iso8583;

import com.thumbzup.iso8583.exception.Iso8583Exception;
import com.thumbzup.iso8583.packager.*;
import com.thumbzup.iso8583.packager.ISOBasePackager;
import org.jpos.iso.*;

import java.util.StringTokenizer;

import javax.xml.bind.DatatypeConverter;

class JposMessage extends ISOMsg implements Iso8583Message {

    private byte[] packCache = null;

    private boolean modified;

    JposMessage(String messageType, byte[] header, AcquirerProtocol protocol) {
        super(messageType);
        if (header != null) {
            setHeader(header);
            setPackager(protocol, header.length);
        } else {
            setPackager(protocol, 0);
        }
    }

    JposMessage(byte[] isoData, int headerLength, AcquirerProtocol protocol) throws Iso8583Exception {
        setPackager(protocol, headerLength);
        try {
            unpack(isoData);
        } catch (ISOException e) {
            throw new Iso8583Exception(e);
        }
        modified = true;
    }

    JposMessage(ISOMsg value, ISOPackager packager) {
        setPackager(packager);
        merge(value);
    }

    public boolean hasPathField(String path) {
        return super.hasField(path);
    }

    private void setPackager(AcquirerProtocol protocol, int headerLength) {
        ImprovedIsoPackager p;
        switch (protocol) {
            case BASE24:
                p = new BASE24Packager();
                break;
            case AS2805:
                p = new AS2805Packager();
                break;
            case POSTBRIDGE:
                p = new PostBridgePackager();
                break;
            case TRADEROUTE:
                p = new TradeRoutePackager();
                break;
            default:
                throw new IllegalArgumentException("Protocol not supported : " + protocol);
        }
        if (headerLength > 0) {
            p.setHeaderLength(headerLength);
        }
        setPackager(p);
    }

    @Override
    public void setField(int no, String value)  {
        modified = true;
        super.set(no, value);
    }

    @Override
    public void setField(String path, String value)  {
        modified = true;
        super.set(path, value);
    }

    @Override
    public void setField(int no, byte[] value) {
        modified = true;
            super.set(no, value);
    }

    @Override
    public void setField(String path, byte[] value) {
        modified = true;
            super.set(path, value);
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
    public boolean isRequestMessage() throws Iso8583Exception {
        try {
            return isRequest();
        } catch (ISOException e) {
            throw new Iso8583Exception(e);
        }
    }

    @Override
    public boolean isResponseMessage() throws Iso8583Exception {
        try {
            return isResponse();
        } catch (ISOException e) {
            throw new Iso8583Exception(e);
        }
    }

    @Override
    public Object getFieldValue(int no) {
            Object value = getValue(no);
            if (value instanceof ISOMsg) {
                ISOMsg value1 = (ISOMsg) value;
                ISOFieldPackager fieldPackager = ((ImprovedIsoPackager) packager).getFieldPackager(no);
                if (fieldPackager instanceof ISOMsgFieldPackager) {
                    ISOMsgFieldPackager isoFieldPackager = (ISOMsgFieldPackager) fieldPackager;
                    return new JposMessage(value1, isoFieldPackager.getISOMsgPackager());
                }
            }
            return value;
    }

    @Override
    public Object getFieldValue(String path) throws Iso8583Exception {
        try {
            return getValue(path);
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
    public String getFieldValueAsString(String path) {
        return getString(path);
    }

    @Override
    public byte[] getFieldValueAsEncoded(int no) throws Iso8583Exception {
        ISOPackager isoPackager = getPackager();
        ISOFieldPackager fieldPackager = ((SimpleBasePackager) isoPackager).getFieldPackager(no);
        try {
            return fieldPackager.pack(getComponent(no));
        } catch (ISOException e) {
            throw new Iso8583Exception(e);
        }
    }

    @Override
    public byte[] getFieldValueAsEncoded(String path) throws Iso8583Exception {
        //TODO : test
        ISOPackager isoPackager = getPackager();
        ISOFieldPackager fieldPackager = ((ImprovedIsoPackager) isoPackager).getFieldPackager(path);
        try {
            return fieldPackager.pack(getComponent(path));
        } catch (ISOException e) {
            throw new Iso8583Exception(e);
        }
    }

    @Override
    public String getFieldDescription(int no) {
        return this.getPackager().getFieldDescription(getComponent(no), no);
    }

    @Override
    public String getFieldDescription(String path) {
        //TODO : show full path
        StringTokenizer st = new StringTokenizer(path, ".");
        int fldno = Integer.parseInt(st.nextToken());
        try {
            return this.getPackager().getFieldDescription(getComponent(path), fldno);
        } catch (ISOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] getFieldValueAsBytes(int no) {
        return getBytes(no);
    }

    @Override
    public byte[] getFieldValueAsBytes(String path) {
        return getBytes(path);
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
