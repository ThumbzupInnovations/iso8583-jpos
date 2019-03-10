package com.thumbzup.iso8583.traderoute;

import org.bouncycastle.util.Encodable;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.util.ArrayList;

abstract class TrArrayList<T> extends ArrayList<T> implements Encodable {

    public TrArrayList(String s) {
        this(DatatypeConverter.parseHexBinary(s));
    }

    public TrArrayList(byte[] data) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dais = new DataInputStream(bais);
        try {
            while (dais.available() > 0) {
                int length = dais.readUnsignedShort();
                byte[] tmp = new byte[length];
                dais.readFully(tmp);
                add(decode(tmp));
            }
        } catch (IOException e) {
        }
    }

    public byte[] getEncoded() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            for (T element : this) {
                byte[] encode = encode(element);
                dos.writeShort(encode.length);
                dos.write(encode);
            }
        } catch (IOException e) {
        }
        return baos.toByteArray();
    }

    protected abstract byte[] encode(T element);

    protected abstract T decode(byte[] tmp);
}
