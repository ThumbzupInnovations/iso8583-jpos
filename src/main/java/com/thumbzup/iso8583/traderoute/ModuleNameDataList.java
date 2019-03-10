package com.thumbzup.iso8583.traderoute;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ModuleNameDataList extends TrArrayList<String> {

    public ModuleNameDataList(String s) {
        super(s);
    }

    public ModuleNameDataList(byte[] data) {
        super(data);
    }

    @Override
    protected byte[] encode(String element) {
        return element.getBytes(StandardCharsets.ISO_8859_1);
    }

    protected String decode(byte[] tmp) {
        return new String(tmp, StandardCharsets.ISO_8859_1);
    }
}
