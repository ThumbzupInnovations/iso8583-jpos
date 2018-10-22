package com.thumbzup.iso8583.traderoute;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;


public class ModuleNameDataListTest {


    @Test
    public void encodeAndDecode() {
        String s = "00285472616465726F6F7451412E5053502E496E746572636F6E6E6563742E50726F7879526F75746572002A5472616465726F6F7451412E5053502E5365637572652E4D65726368616E74416363657373506F696E7400205472616465726F6F7451412E5053502E5365637572652E456674537769746368";
        TrArrayList<String> tradeRouteArray = new ModuleNameDataList(s);
        Assert.assertEquals("TraderootQA.PSP.Interconnect.ProxyRouter", tradeRouteArray.get(0));
        Assert.assertEquals("TraderootQA.PSP.Secure.MerchantAccessPoint", tradeRouteArray.get(1));
        Assert.assertEquals("TraderootQA.PSP.Secure.EftSwitch", tradeRouteArray.get(2));
        Assert.assertEquals(s, DatatypeConverter.printHexBinary(tradeRouteArray.getEncoded()));
    }
}
