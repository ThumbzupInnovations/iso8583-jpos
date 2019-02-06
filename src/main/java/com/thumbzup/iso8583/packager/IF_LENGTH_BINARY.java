package com.thumbzup.iso8583.packager;

import org.jpos.iso.*;

public class IF_LENGTH_BINARY extends ISOBinaryFieldPackager {

    public IF_LENGTH_BINARY(int byteCount, String description) {
        super(Integer.MAX_VALUE, description, LiteralBinaryInterpreter.INSTANCE, new BinaryPrefixer(byteCount));
    }

    @Override
    public int unpack(ISOComponent c, byte[] b, int offset) throws ISOException {
        if (offset == b.length) {
            return 0;
        }
        return super.unpack(c, b, offset);
    }

    @Override
    public byte[] pack(ISOComponent c) throws ISOException {
        if (c.getValue() == null) {
            return new byte[0];
        }
        return super.pack(c);
    }

}
