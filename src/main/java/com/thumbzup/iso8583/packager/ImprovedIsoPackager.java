package com.thumbzup.iso8583.packager;

import org.jpos.iso.ISOFieldPackager;
import org.jpos.iso.ISOPackager;

public interface ImprovedIsoPackager extends ISOPackager {
    int getHeaderLength();

    void setHeaderLength(int len);

    ISOFieldPackager getFieldPackager(String path);

    ISOFieldPackager getFieldPackager(int fieldNo);
}
