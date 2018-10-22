package com.thumbzup.iso8583.traderoute;

public class ModuleStampDataList extends TrArrayList<ModuleStamp> {
    public ModuleStampDataList(String s) {
        super(s);
    }

    public ModuleStampDataList(byte[] data) {
        super(data);
    }

    @Override
    protected byte[] encode(ModuleStamp element) {
        return element.getEncoded();
    }

    @Override
    protected ModuleStamp decode(byte[] tmp) {
        return new ModuleStamp(tmp);
    }
}
