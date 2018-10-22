package com.thumbzup.iso8583.traderoute;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;

import static com.thumbzup.iso8583.traderoute.ModuleStamp.Direction.*;


public class ModuleStampDataListTest {
    @Test
    public void testEncodeDecode() {
        String data = "00060137E60F303000060237E60F303000060137E60F303100060237E60F303100060137E60F303200060237E60F3032";
        ModuleStampDataList dataList = new ModuleStampDataList(data);
        Assert.assertEquals(RX, dataList.get(0).getMessageTransitDirection());
        Assert.assertEquals(3663375, dataList.get(0).getMessageTransitTimeStamp());
        Assert.assertEquals("00", dataList.get(0).getModulePublicNameIndex());

        Assert.assertEquals(TX, dataList.get(1).getMessageTransitDirection());
        Assert.assertEquals(3663375, dataList.get(1).getMessageTransitTimeStamp());
        Assert.assertEquals("00", dataList.get(1).getModulePublicNameIndex());

        Assert.assertEquals(RX, dataList.get(2).getMessageTransitDirection());
        Assert.assertEquals(3663375, dataList.get(2).getMessageTransitTimeStamp());
        Assert.assertEquals("01", dataList.get(2).getModulePublicNameIndex());

        Assert.assertEquals(TX, dataList.get(3).getMessageTransitDirection());
        Assert.assertEquals(3663375, dataList.get(3).getMessageTransitTimeStamp());
        Assert.assertEquals("01", dataList.get(3).getModulePublicNameIndex());

        Assert.assertEquals(RX, dataList.get(4).getMessageTransitDirection());
        Assert.assertEquals(3663375, dataList.get(4).getMessageTransitTimeStamp());
        Assert.assertEquals("02", dataList.get(4).getModulePublicNameIndex());

        Assert.assertEquals(TX, dataList.get(5).getMessageTransitDirection());
        Assert.assertEquals(3663375, dataList.get(5).getMessageTransitTimeStamp());
        Assert.assertEquals("02", dataList.get(5).getModulePublicNameIndex());

        dataList.set(5, new ModuleStamp(ModuleStamp.Direction.TX,3663375,"02"));

        Assert.assertEquals(data, DatatypeConverter.printHexBinary(dataList.getEncoded()));

    }
}
