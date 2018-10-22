package com.thumbzup.iso8583;

import com.thumbzup.iso8583.exception.Iso8583Exception;
import com.thumbzup.iso8583.traderoute.ModuleNameDataList;
import com.thumbzup.iso8583.traderoute.ModuleStamp;
import com.thumbzup.iso8583.traderoute.ModuleStampDataList;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;

import static com.thumbzup.iso8583.FieldConstants.*;

/**
 * Created by Sean on 2016/09/20.
 */
public class MessageFactoryTest {
    @Test
    public void testEcho() throws Exception {
        String lexicalXSDHexBinary = "30383030823800000000000004000000000000023130303431303030313130303132393831323030313131303034333031004000000040000030303131303438002200205472616465726F6F7451412E5053502E5365637572652E456674537769746368000800060237DBA330303030";
        Iso8583Message msg = Iso8583MessageFactory.parse(DatatypeConverter.parseHexBinary(lexicalXSDHexBinary), AcquirerProtocol.TRADEROUTE, 0);
        Assert.assertEquals("0800", msg.getMessageType());
        Assert.assertEquals("1004100011", msg.getFieldValueAsString(FIELD_7));
        Assert.assertEquals("001298", msg.getFieldValueAsString(FIELD_11));
        Assert.assertEquals("120011", msg.getFieldValueAsString(FIELD_12));
        Assert.assertEquals("1004", msg.getFieldValueAsString(FIELD_13));
        Assert.assertEquals("301", msg.getFieldValueAsString(FIELD_70));
        Assert.assertEquals("1", msg.getFieldValueAsString(FIELD_138));
        Assert.assertEquals("00", msg.getFieldValueAsString("170.2"));

        Assert.assertEquals("TraderootQA.PSP.Secure.EftSwitch", new ModuleNameDataList(msg.getFieldValueAsBytes("170.0")).get(0));

        Assert.assertEquals(lexicalXSDHexBinary, DatatypeConverter.printHexBinary(msg.encode()));
    }

    @Test
    public void testEchoResponse() throws Exception {
        String lexicalXSDHexBinary = "3038313082380000020000000400000000000002313030343130303031313030313239383132303031313130303430303330318040000000400000303034342E303130303131303536002200205472616465726F6F7451412E5053502E5365637572652E456674537769746368001000060237DBA3303000060137DBA330303031";
        Iso8583Message msg = Iso8583MessageFactory.parse(DatatypeConverter.parseHexBinary(lexicalXSDHexBinary), AcquirerProtocol.TRADEROUTE, 0);
        Assert.assertEquals("0810", msg.getMessageType());
        Assert.assertEquals("1004100011", msg.getFieldValueAsString(FIELD_7));
        Assert.assertEquals("001298", msg.getFieldValueAsString(FIELD_11));
        Assert.assertEquals("120011", msg.getFieldValueAsString(FIELD_12));
        Assert.assertEquals("1004", msg.getFieldValueAsString(FIELD_13));
        Assert.assertEquals("00", msg.getFieldValueAsString(FIELD_39));
        Assert.assertEquals("301", msg.getFieldValueAsString(FIELD_70));
        Assert.assertEquals("4.01", msg.getFieldValueAsString(FIELD_129));
        Assert.assertEquals("1", msg.getFieldValueAsString(FIELD_138));
        Assert.assertEquals("01", msg.getFieldValueAsString("170.2"));

        ModuleStampDataList moduleNameData = new ModuleStampDataList(msg.getFieldValueAsBytes("170.1"));
        Assert.assertEquals(ModuleStamp.Direction.RX, moduleNameData.get(1).getMessageTransitDirection());
        Assert.assertEquals(3660707, moduleNameData.get(1).getMessageTransitTimeStamp());
        Assert.assertEquals("00", moduleNameData.get(1).getModulePublicNameIndex());

        Assert.assertEquals(lexicalXSDHexBinary, DatatypeConverter.printHexBinary(msg.encode()));
    }

    @Test
    public void testLogon() throws Exception {
        String lexicalXSDHexBinary = "30383030823800000000000004000000000000023130303931313238303830303030303131333238303831303039303031004000000040000030303131303539002D002B5472616465526F6F74446576656C6F706D656E742E54657374696E672E546573742E434548536C617665730008000602482B6930303030";
        Iso8583Message msg = Iso8583MessageFactory.parse(DatatypeConverter.parseHexBinary(lexicalXSDHexBinary), AcquirerProtocol.TRADEROUTE, 0);
        Assert.assertEquals("0800", msg.getMessageType());
        Assert.assertEquals("1009112808", msg.getFieldValueAsString(FIELD_7));
        Assert.assertEquals("000001", msg.getFieldValueAsString(FIELD_11));
        Assert.assertEquals("132808", msg.getFieldValueAsString(FIELD_12));
        Assert.assertEquals("1009", msg.getFieldValueAsString(FIELD_13));
        Assert.assertEquals("001", msg.getFieldValueAsString(FIELD_70));
        Assert.assertEquals("1", msg.getFieldValueAsString(FIELD_138));
        Assert.assertEquals("00", msg.getFieldValueAsString("170.2"));
        Assert.assertEquals(lexicalXSDHexBinary, DatatypeConverter.printHexBinary(msg.encode()));
    }

    @Test
    public void testLogonResponse() throws Exception {
        String lexicalXSDHexBinary = "3038313082380000020000000400000000000002313030393131323830383030303030313133323830383130303930303030310040000000400000303031313131370057002B5472616465526F6F74446576656C6F706D656E742E54657374696E672E546573742E434548536C6176657300285472616465526F6F74446576656C6F706D656E742E54657374696E672E546573742E42696349736F0018000602482B693030000601482BD73031000602482C0930313032";
        Iso8583Message msg = Iso8583MessageFactory.parse(DatatypeConverter.parseHexBinary(lexicalXSDHexBinary), AcquirerProtocol.TRADEROUTE, 0);
        Assert.assertEquals("0810", msg.getMessageType());
        Assert.assertEquals("1009112808", msg.getFieldValueAsString(FIELD_7));
        Assert.assertEquals("000001", msg.getFieldValueAsString(FIELD_11));
        Assert.assertEquals("132808", msg.getFieldValueAsString(FIELD_12));
        Assert.assertEquals("1009", msg.getFieldValueAsString(FIELD_13));
        Assert.assertEquals("00", msg.getFieldValueAsString(FIELD_39));
        Assert.assertEquals("001", msg.getFieldValueAsString(FIELD_70));
        Assert.assertEquals("1", msg.getFieldValueAsString(FIELD_138));
        Assert.assertEquals("02", msg.getFieldValueAsString("170.2"));

        ModuleNameDataList nameDataList = new ModuleNameDataList(msg.getFieldValueAsBytes("170.0"));

        Assert.assertEquals("TradeRootDevelopment.Testing.Test.CEHSlaves",nameDataList.get(0));
        Assert.assertEquals("TradeRootDevelopment.Testing.Test.BicIso",nameDataList.get(1));
        Assert.assertEquals(lexicalXSDHexBinary, DatatypeConverter.printHexBinary(msg.encode()));
    }

    @Test
    public void testAuth0100() throws Exception {
        String lexicalXSDHexBinary = "30313030F23844C129E098000000000000000002313735383838393239303830313937353338353030303030303030303030303030313130303130303431303033333636363333303631323033333631303034353431313930313030303631313030313233343536373839333735383838393239303830313937353338353D3D3D3838333930303230303030303030303032313231303034313230363037333930303030303030303131353730303033303030303030303147454E4552414C204D45524348414E54202020202020202020202020202020202020202057435A41373130A432591C66B82E742057059000E0002D00000000000000000000000000000000000000000000000000000000000000000000000000000000F2C0000000400000303034342E30313031313130313230313431353533303131353435383335383535653064396136642D303663382D343632372D623638362D66393161633230343462373930303132313734007800285472616465726F6F7451412E5053502E496E746572636F6E6E6563742E50726F7879526F75746572002A5472616465726F6F7451412E5053502E5365637572652E4D65726368616E74416363657373506F696E7400205472616465726F6F7451412E5053502E5365637572652E45667453776974636800300006013429D430300006023429D430300006013429D43031000602342A503031000601342A603032000602342A7F30323035";
        Iso8583Message msg = Iso8583MessageFactory.parse(DatatypeConverter.parseHexBinary(lexicalXSDHexBinary), AcquirerProtocol.TRADEROUTE, 0);
        Assert.assertEquals("0100", msg.getMessageType());
        Assert.assertEquals("58889290801975385", msg.getFieldValueAsString(FIELD_2));
        Assert.assertEquals("000000", msg.getFieldValueAsString(FIELD_3));
        Assert.assertEquals("000000001100", msg.getFieldValueAsString(FIELD_4));
        Assert.assertEquals("1004100336", msg.getFieldValueAsString(FIELD_7));
        Assert.assertEquals("663306", msg.getFieldValueAsString(FIELD_11));
        Assert.assertEquals("120336", msg.getFieldValueAsString(FIELD_12));
        Assert.assertEquals("1004", msg.getFieldValueAsString(FIELD_13));
        Assert.assertEquals("5411", msg.getFieldValueAsString(FIELD_18));
        Assert.assertEquals("901", msg.getFieldValueAsString(FIELD_22));
        Assert.assertEquals("00", msg.getFieldValueAsString(FIELD_25));
        Assert.assertEquals("06", msg.getFieldValueAsString(FIELD_26));

        Assert.assertEquals("00123456789", msg.getFieldValueAsString(FIELD_32));
        Assert.assertEquals("58889290801975385===88390020000000002", msg.getFieldValueAsString(FIELD_35));
        Assert.assertEquals("121004120607", msg.getFieldValueAsString(FIELD_37));
        Assert.assertEquals("390", msg.getFieldValueAsString(FIELD_40));
        Assert.assertEquals("00000001", msg.getFieldValueAsString(FIELD_41));
        Assert.assertEquals("157000300000001", msg.getFieldValueAsString(FIELD_42));
        Assert.assertEquals("GENERAL MERCHANT                    WCZA", msg.getFieldValueAsString(FIELD_43));
        Assert.assertEquals("710", msg.getFieldValueAsString(FIELD_49));

        Assert.assertEquals("A432591C66B82E74", msg.getFieldValueAsString(FIELD_52));
        Assert.assertEquals("2057059000E0002D00000000000000000000000000000000000000000000000000000000000000000000000000000000", msg.getFieldValueAsString(FIELD_53));

        Assert.assertEquals("4.01", msg.getFieldValueAsString(FIELD_129));

        Assert.assertEquals("01", msg.getFieldValueAsString("130.0"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.1"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.2"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.3"));
        Assert.assertEquals("12", msg.getFieldValueAsString("130.4"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.5"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.6"));
        Assert.assertEquals("4", msg.getFieldValueAsString("130.7"));

        Assert.assertEquals("1", msg.getFieldValueAsString("131.0"));
        Assert.assertEquals("5", msg.getFieldValueAsString("131.1"));
        Assert.assertEquals("5", msg.getFieldValueAsString("131.2"));
        Assert.assertEquals("3", msg.getFieldValueAsString("131.3"));

        Assert.assertEquals("0", msg.getFieldValueAsString("132.0"));
        Assert.assertEquals("1", msg.getFieldValueAsString("132.1"));
        Assert.assertEquals("1", msg.getFieldValueAsString("132.2"));

        Assert.assertEquals("54583585", msg.getFieldValueAsString("135"));
        Assert.assertEquals("5e0d9a6d-06c8-4627-b686-f91ac2044b79", msg.getFieldValueAsString("137"));
        Assert.assertEquals("2", msg.getFieldValueAsString("138"));

        Assert.assertEquals("05", msg.getFieldValueAsString("170.2"));
        Assert.assertEquals(lexicalXSDHexBinary, DatatypeConverter.printHexBinary(msg.encode()));
    }

    @Test
    public void testAuth0100Response() throws Exception {
        String lexicalXSDHexBinary = "30313130F23844C12FE098000000000000000002313735383838393239303830313937353338353030303030303030303030303030313130303130303431303033333636363333303631323033333631303034353431313930313030303631313030313233343536373839333735383838393239303830313937353338353D3D3D38383339303032303030303030303030323132313030343132303630373438303532303030333930303030303030303131353730303033303030303030303147454E4552414C204D45524348414E54202020202020202020202020202020202020202057435A41373130A432591C66B82E742057059000E0002D00000000000000000000000000000000000000000000000000000000000000000000000000000000F2C0000000400000303034342E30313031313130313230313431353533303131353435383335383535653064396136642D303663382D343632372D623638362D6639316163323034346237393030313232343100A300285472616465726F6F7451412E5053502E496E746572636F6E6E6563742E50726F7879526F75746572002A5472616465726F6F7451412E5053502E5365637572652E4D65726368616E74416363657373506F696E7400205472616465726F6F7451412E5053502E5365637572652E45667453776974636800295472616465726F6F7451412E5053502E496E746572636F6E6E6563742E49736F53696D756C61746F7200480006013429D430300006023429D430300006013429D43031000602342A503031000601342A603032000602342A7F3032000601342A9E3033000602342ABE3033000601342ABE30323038";
        Iso8583Message msg = Iso8583MessageFactory.parse(DatatypeConverter.parseHexBinary(lexicalXSDHexBinary), AcquirerProtocol.TRADEROUTE, 0);

        Assert.assertEquals("0110", msg.getMessageType());
        Assert.assertEquals("58889290801975385", msg.getFieldValueAsString(FIELD_2));
        Assert.assertEquals("000000", msg.getFieldValueAsString(FIELD_3));
        Assert.assertEquals("000000001100", msg.getFieldValueAsString(FIELD_4));
        Assert.assertEquals("1004100336", msg.getFieldValueAsString(FIELD_7));
        Assert.assertEquals("663306", msg.getFieldValueAsString(FIELD_11));
        Assert.assertEquals("120336", msg.getFieldValueAsString(FIELD_12));
        Assert.assertEquals("1004", msg.getFieldValueAsString(FIELD_13));
        Assert.assertEquals("5411", msg.getFieldValueAsString(FIELD_18));
        Assert.assertEquals("901", msg.getFieldValueAsString(FIELD_22));
        Assert.assertEquals("00", msg.getFieldValueAsString(FIELD_25));
        Assert.assertEquals("06", msg.getFieldValueAsString(FIELD_26));

        Assert.assertEquals("00123456789", msg.getFieldValueAsString(FIELD_32));
        Assert.assertEquals("58889290801975385===88390020000000002", msg.getFieldValueAsString(FIELD_35));
        Assert.assertEquals("121004120607", msg.getFieldValueAsString(FIELD_37));

        Assert.assertEquals("480520", msg.getFieldValueAsString(FIELD_38));
        Assert.assertEquals("00", msg.getFieldValueAsString(FIELD_39));

        Assert.assertEquals("390", msg.getFieldValueAsString(FIELD_40));
        Assert.assertEquals("00000001", msg.getFieldValueAsString(FIELD_41));
        Assert.assertEquals("157000300000001", msg.getFieldValueAsString(FIELD_42));
        Assert.assertEquals("GENERAL MERCHANT                    WCZA", msg.getFieldValueAsString(FIELD_43));
        Assert.assertEquals("710", msg.getFieldValueAsString(FIELD_49));

        Assert.assertEquals("A432591C66B82E74", msg.getFieldValueAsString(FIELD_52));
        Assert.assertEquals("2057059000E0002D00000000000000000000000000000000000000000000000000000000000000000000000000000000", msg.getFieldValueAsString(FIELD_53));

        Assert.assertEquals("4.01", msg.getFieldValueAsString(FIELD_129));

        Assert.assertEquals("01", msg.getFieldValueAsString("130.0"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.1"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.2"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.3"));
        Assert.assertEquals("12", msg.getFieldValueAsString("130.4"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.5"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.6"));
        Assert.assertEquals("4", msg.getFieldValueAsString("130.7"));

        Assert.assertEquals("1", msg.getFieldValueAsString("131.0"));
        Assert.assertEquals("5", msg.getFieldValueAsString("131.1"));
        Assert.assertEquals("5", msg.getFieldValueAsString("131.2"));
        Assert.assertEquals("3", msg.getFieldValueAsString("131.3"));

        Assert.assertEquals("0", msg.getFieldValueAsString("132.0"));
        Assert.assertEquals("1", msg.getFieldValueAsString("132.1"));
        Assert.assertEquals("1", msg.getFieldValueAsString("132.2"));

        Assert.assertEquals("54583585", msg.getFieldValueAsString("135"));
        Assert.assertEquals("5e0d9a6d-06c8-4627-b686-f91ac2044b79", msg.getFieldValueAsString("137"));
        Assert.assertEquals("2", msg.getFieldValueAsString("138"));

        Assert.assertEquals("08", msg.getFieldValueAsString("170.2"));
        Assert.assertEquals(lexicalXSDHexBinary, DatatypeConverter.printHexBinary(msg.encode()));
    }

    @Test
    public void testSettlement0220() throws Exception {
        String lexicalXSDHexBinary = "30323230F23844C12FE098000000004200000002313735383838393239303830313937353338353030303030303030303030303030313130303130303431303033343436363333303631323033333631303034353431313930313030303631313030313233343536373839333735383838393239303830313937353338353D3D3D38383339303032303030303030303030323132313030343132303630373438303532303030333930303030303030303131353730303033303030303030303147454E4552414C204D45524348414E54202020202020202020202020202020202020202057435A41373130A432591C66B82E742057059000E0002D00000000000000000000000000000000000000000000000000000000000000000000000000000000303130303636333330363130303431303033333630303132333435363738393030303030303030303030303030303030303031313030303030303030303031313030433030303030303030433030303030303030F2C0000000400000303034342E30313031313130313230313431353533303131353435383335383535653064396136642D303663382D343632372D623638362D66393161633230343462373930303132313734007800285472616465726F6F7451412E5053502E496E746572636F6E6E6563742E50726F7879526F75746572002A5472616465726F6F7451412E5053502E5365637572652E4D65726368616E74416363657373506F696E7400205472616465726F6F7451412E5053502E5365637572652E4566745377697463680030000601344AAC3030000602344AAC3030000601344ABC3031000602344ABC3031000601344ABC3032000602344ABC30323035";
        Iso8583Message msg = Iso8583MessageFactory.parse(DatatypeConverter.parseHexBinary(lexicalXSDHexBinary), AcquirerProtocol.TRADEROUTE, 0);

        Assert.assertEquals("0220", msg.getMessageType());
        Assert.assertEquals("58889290801975385", msg.getFieldValueAsString(FIELD_2));
        Assert.assertEquals("000000", msg.getFieldValueAsString(FIELD_3));
        Assert.assertEquals("000000001100", msg.getFieldValueAsString(FIELD_4));
        Assert.assertEquals("1004100344", msg.getFieldValueAsString(FIELD_7));
        Assert.assertEquals("663306", msg.getFieldValueAsString(FIELD_11));
        Assert.assertEquals("120336", msg.getFieldValueAsString(FIELD_12));
        Assert.assertEquals("1004", msg.getFieldValueAsString(FIELD_13));

        Assert.assertEquals("5411", msg.getFieldValueAsString(FIELD_18));
        Assert.assertEquals("901", msg.getFieldValueAsString(FIELD_22));
        Assert.assertEquals("00", msg.getFieldValueAsString(FIELD_25));
        Assert.assertEquals("06", msg.getFieldValueAsString(FIELD_26));

        Assert.assertEquals("00123456789", msg.getFieldValueAsString(FIELD_32));
        Assert.assertEquals("58889290801975385===88390020000000002", msg.getFieldValueAsString(FIELD_35));
        Assert.assertEquals("121004120607", msg.getFieldValueAsString(FIELD_37));

        Assert.assertEquals("480520", msg.getFieldValueAsString(FIELD_38));
        Assert.assertEquals("00", msg.getFieldValueAsString(FIELD_39));

        Assert.assertEquals("390", msg.getFieldValueAsString(FIELD_40));
        Assert.assertEquals("00000001", msg.getFieldValueAsString(FIELD_41));
        Assert.assertEquals("157000300000001", msg.getFieldValueAsString(FIELD_42));
        Assert.assertEquals("GENERAL MERCHANT                    WCZA", msg.getFieldValueAsString(FIELD_43));
        Assert.assertEquals("710", msg.getFieldValueAsString(FIELD_49));

        Assert.assertEquals("A432591C66B82E74", msg.getFieldValueAsString(FIELD_52));
        Assert.assertEquals("2057059000E0002D00000000000000000000000000000000000000000000000000000000000000000000000000000000", msg.getFieldValueAsString(FIELD_53));

        Assert.assertEquals("010066330610041003360012345678900000000000", msg.getFieldValueAsString(FIELD_90));
        Assert.assertEquals("000000001100000000001100C00000000C00000000", msg.getFieldValueAsString(FIELD_95));

        Assert.assertEquals("4.01", msg.getFieldValueAsString(FIELD_129));

        Assert.assertEquals("01", msg.getFieldValueAsString("130.0"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.1"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.2"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.3"));
        Assert.assertEquals("12", msg.getFieldValueAsString("130.4"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.5"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.6"));
        Assert.assertEquals("4", msg.getFieldValueAsString("130.7"));

        Assert.assertEquals("1", msg.getFieldValueAsString("131.0"));
        Assert.assertEquals("5", msg.getFieldValueAsString("131.1"));
        Assert.assertEquals("5", msg.getFieldValueAsString("131.2"));
        Assert.assertEquals("3", msg.getFieldValueAsString("131.3"));

        Assert.assertEquals("0", msg.getFieldValueAsString("132.0"));
        Assert.assertEquals("1", msg.getFieldValueAsString("132.1"));
        Assert.assertEquals("1", msg.getFieldValueAsString("132.2"));

        Assert.assertEquals("54583585", msg.getFieldValueAsString("135"));
        Assert.assertEquals("5e0d9a6d-06c8-4627-b686-f91ac2044b79", msg.getFieldValueAsString("137"));
        Assert.assertEquals("2", msg.getFieldValueAsString("138"));

        Assert.assertEquals("05", msg.getFieldValueAsString("170.2"));
        Assert.assertEquals(lexicalXSDHexBinary, DatatypeConverter.printHexBinary(msg.encode()));
    }

    @Test
    public void testSettlement0230() throws Exception {
        String lexicalXSDHexBinary = "30323330F23844C12FE098000000004200000002313735383838393239303830313937353338353030303030303030303030303030313130303130303431303033343436363333303631323033333631303034353431313930313030303631313030313233343536373839333735383838393239303830313937353338353D3D3D38383339303032303030303030303030323132313030343132303630373438303532303030333930303030303030303131353730303033303030303030303147454E4552414C204D45524348414E54202020202020202020202020202020202020202057435A41373130A432591C66B82E742057059000E0002D00000000000000000000000000000000000000000000000000000000000000000000000000000000303130303636333330363130303431303033333630303132333435363738393030303030303030303030303030303030303031313030303030303030303031313030433030303030303030433030303030303030F2C0000000400000303034342E30313031313130313230313431353533303131353435383335383535653064396136642D303663382D343632372D623638362D6639316163323034346237393030313232343100A300285472616465726F6F7451412E5053502E496E746572636F6E6E6563742E50726F7879526F75746572002A5472616465726F6F7451412E5053502E5365637572652E4D65726368616E74416363657373506F696E7400205472616465726F6F7451412E5053502E5365637572652E45667453776974636800295472616465726F6F7451412E5053502E496E746572636F6E6E6563742E49736F53696D756C61746F720048000601344AAC3030000602344AAC3030000601344ABC3031000602344ABC3031000601344ABC3032000602344ABC3032000601344ACB3033000602344ACB3033000601344ADB30323038";
        Iso8583Message msg = Iso8583MessageFactory.parse(DatatypeConverter.parseHexBinary(lexicalXSDHexBinary), AcquirerProtocol.TRADEROUTE, 0);

        Assert.assertEquals("0230", msg.getMessageType());
        Assert.assertEquals("58889290801975385", msg.getFieldValueAsString(FIELD_2));
        Assert.assertEquals("000000", msg.getFieldValueAsString(FIELD_3));
        Assert.assertEquals("000000001100", msg.getFieldValueAsString(FIELD_4));
        Assert.assertEquals("1004100344", msg.getFieldValueAsString(FIELD_7));
        Assert.assertEquals("663306", msg.getFieldValueAsString(FIELD_11));
        Assert.assertEquals("120336", msg.getFieldValueAsString(FIELD_12));
        Assert.assertEquals("1004", msg.getFieldValueAsString(FIELD_13));

        Assert.assertEquals("5411", msg.getFieldValueAsString(FIELD_18));
        Assert.assertEquals("901", msg.getFieldValueAsString(FIELD_22));
        Assert.assertEquals("00", msg.getFieldValueAsString(FIELD_25));
        Assert.assertEquals("06", msg.getFieldValueAsString(FIELD_26));

        Assert.assertEquals("00123456789", msg.getFieldValueAsString(FIELD_32));
        Assert.assertEquals("58889290801975385===88390020000000002", msg.getFieldValueAsString(FIELD_35));
        Assert.assertEquals("121004120607", msg.getFieldValueAsString(FIELD_37));



        Assert.assertEquals("390", msg.getFieldValueAsString(FIELD_40));
        Assert.assertEquals("00000001", msg.getFieldValueAsString(FIELD_41));
        Assert.assertEquals("157000300000001", msg.getFieldValueAsString(FIELD_42));
        Assert.assertEquals("GENERAL MERCHANT                    WCZA", msg.getFieldValueAsString(FIELD_43));
        Assert.assertEquals("710", msg.getFieldValueAsString(FIELD_49));

        Assert.assertEquals("A432591C66B82E74", msg.getFieldValueAsString(FIELD_52));
        Assert.assertEquals("2057059000E0002D00000000000000000000000000000000000000000000000000000000000000000000000000000000", msg.getFieldValueAsString(FIELD_53));

        Assert.assertEquals("010066330610041003360012345678900000000000", msg.getFieldValueAsString(FIELD_90));
        Assert.assertEquals("000000001100000000001100C00000000C00000000", msg.getFieldValueAsString(FIELD_95));

        Assert.assertEquals("4.01", msg.getFieldValueAsString(FIELD_129));

        Assert.assertEquals("01", msg.getFieldValueAsString("130.0"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.1"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.2"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.3"));
        Assert.assertEquals("12", msg.getFieldValueAsString("130.4"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.5"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.6"));
        Assert.assertEquals("4", msg.getFieldValueAsString("130.7"));

        Assert.assertEquals("1", msg.getFieldValueAsString("131.0"));
        Assert.assertEquals("5", msg.getFieldValueAsString("131.1"));
        Assert.assertEquals("5", msg.getFieldValueAsString("131.2"));
        Assert.assertEquals("3", msg.getFieldValueAsString("131.3"));

        Assert.assertEquals("0", msg.getFieldValueAsString("132.0"));
        Assert.assertEquals("1", msg.getFieldValueAsString("132.1"));
        Assert.assertEquals("1", msg.getFieldValueAsString("132.2"));

        Assert.assertEquals("54583585", msg.getFieldValueAsString("135"));
        Assert.assertEquals("5e0d9a6d-06c8-4627-b686-f91ac2044b79", msg.getFieldValueAsString("137"));
        Assert.assertEquals("2", msg.getFieldValueAsString("138"));

        Assert.assertEquals("08", msg.getFieldValueAsString("170.2"));
        Assert.assertEquals(lexicalXSDHexBinary, DatatypeConverter.printHexBinary(msg.encode()));
    }

    @Test
    public void testAuth0200() throws Exception {
        String lexicalXSDHexBinary = "30323030F23C44C129E0980000000000000000023136353033363136333330323435323935313030313030303030303030303030313030303130303431303037343137333236373631323037343131303034323130383534313139303130303036313130303132333435363738393337353033363136333330323435323935313D3231303831323030303030303133303131303030313231303034313231303230313230303030303030303131353730303033303030303030303147454E4552414C204D45524348414E54202020202020202020202020202020202020202057435A41373130306B9B09520799F02057059000E0002F00000000000000000000000000000000000000000000000000000000000000000000000000000000F2C0000000400000303034342E30313031313130313230313431353533303131383032303839363462393631313737392D306434322D346634332D623338382D63626335383136386536613730303132313734007800285472616465726F6F7451412E5053502E496E746572636F6E6E6563742E50726F7879526F75746572002A5472616465726F6F7451412E5053502E5365637572652E4D65726368616E74416363657373506F696E7400205472616465726F6F7451412E5053502E5365637572652E456674537769746368003000060137E60F303000060237E60F303000060137E60F303100060237E60F303100060137E60F303200060237E60F30323035";
        Iso8583Message msg = Iso8583MessageFactory.parse(DatatypeConverter.parseHexBinary(lexicalXSDHexBinary), AcquirerProtocol.TRADEROUTE, 0);
        Assert.assertEquals("0200", msg.getMessageType());
        Assert.assertEquals("5036163302452951", msg.getFieldValueAsString(FIELD_2));
        Assert.assertEquals("001000", msg.getFieldValueAsString(FIELD_3));
        Assert.assertEquals("000000001000", msg.getFieldValueAsString(FIELD_4));
        Assert.assertEquals("1004100741", msg.getFieldValueAsString(FIELD_7));
        Assert.assertEquals("732676", msg.getFieldValueAsString(FIELD_11));
        Assert.assertEquals("120741", msg.getFieldValueAsString(FIELD_12));
        Assert.assertEquals("1004", msg.getFieldValueAsString(FIELD_13));
        Assert.assertEquals("2108", msg.getFieldValueAsString(FIELD_14));
        Assert.assertEquals("5411", msg.getFieldValueAsString(FIELD_18));
        Assert.assertEquals("901", msg.getFieldValueAsString(FIELD_22));
        Assert.assertEquals("00", msg.getFieldValueAsString(FIELD_25));
        Assert.assertEquals("06", msg.getFieldValueAsString(FIELD_26));

        Assert.assertEquals("00123456789", msg.getFieldValueAsString(FIELD_32));
        Assert.assertEquals("5036163302452951=21081200000013011000", msg.getFieldValueAsString(FIELD_35));
        Assert.assertEquals("121004121020", msg.getFieldValueAsString(FIELD_37));

        Assert.assertEquals("120", msg.getFieldValueAsString(FIELD_40));
        Assert.assertEquals("00000001", msg.getFieldValueAsString(FIELD_41));
        Assert.assertEquals("157000300000001", msg.getFieldValueAsString(FIELD_42));
        Assert.assertEquals("GENERAL MERCHANT                    WCZA", msg.getFieldValueAsString(FIELD_43));
        Assert.assertEquals("710", msg.getFieldValueAsString(FIELD_49));

        Assert.assertEquals("306B9B09520799F0", msg.getFieldValueAsString(FIELD_52));
        Assert.assertEquals("2057059000E0002F00000000000000000000000000000000000000000000000000000000000000000000000000000000", msg.getFieldValueAsString(FIELD_53));

        Assert.assertEquals("4.01", msg.getFieldValueAsString(FIELD_129));

        Assert.assertEquals("01", msg.getFieldValueAsString("130.0"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.1"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.2"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.3"));
        Assert.assertEquals("12", msg.getFieldValueAsString("130.4"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.5"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.6"));
        Assert.assertEquals("4", msg.getFieldValueAsString("130.7"));

        Assert.assertEquals("1", msg.getFieldValueAsString("131.0"));
        Assert.assertEquals("5", msg.getFieldValueAsString("131.1"));
        Assert.assertEquals("5", msg.getFieldValueAsString("131.2"));
        Assert.assertEquals("3", msg.getFieldValueAsString("131.3"));

        Assert.assertEquals("0", msg.getFieldValueAsString("132.0"));
        Assert.assertEquals("1", msg.getFieldValueAsString("132.1"));
        Assert.assertEquals("1", msg.getFieldValueAsString("132.2"));

        Assert.assertEquals("80208964", msg.getFieldValueAsString("135"));
        Assert.assertEquals("b9611779-0d42-4f43-b388-cbc58168e6a7", msg.getFieldValueAsString("137"));
        Assert.assertEquals("2", msg.getFieldValueAsString("138"));

        Assert.assertEquals("05", msg.getFieldValueAsString("170.2"));
        Assert.assertEquals(lexicalXSDHexBinary, DatatypeConverter.printHexBinary(msg.encode()));
    }

    @Test
    public void testAuth0210() throws Exception {
        String lexicalXSDHexBinary = "30323130F23C44C12FE0980000000000000000023136353033363136333330323435323935313030313030303030303030303030313030303130303431303037343137333236373631323037343131303034323130383534313139303130303036313130303132333435363738393337353033363136333330323435323935313D32313038313230303030303031333031313030303132313030343132313032303438303532343030313230303030303030303131353730303033303030303030303147454E4552414C204D45524348414E54202020202020202020202020202020202020202057435A41373130306B9B09520799F02057059000E0002F00000000000000000000000000000000000000000000000000000000000000000000000000000000F2C0000000400000303034342E30313031313130313230313431353533303131383032303839363462393631313737392D306434322D346634332D623338382D6362633538313638653661373030313232343100A300285472616465726F6F7451412E5053502E496E746572636F6E6E6563742E50726F7879526F75746572002A5472616465726F6F7451412E5053502E5365637572652E4D65726368616E74416363657373506F696E7400205472616465726F6F7451412E5053502E5365637572652E45667453776974636800295472616465726F6F7451412E5053502E496E746572636F6E6E6563742E49736F53696D756C61746F72004800060137E60F303000060237E60F303000060137E60F303100060237E60F303100060137E60F303200060237E60F303200060137E60F303300060237E60F303300060137E61E30323038";
        Iso8583Message msg = Iso8583MessageFactory.parse(DatatypeConverter.parseHexBinary(lexicalXSDHexBinary), AcquirerProtocol.TRADEROUTE, 0);
        Assert.assertEquals("0210", msg.getMessageType());
        Assert.assertEquals("5036163302452951", msg.getFieldValueAsString(FIELD_2));
        Assert.assertEquals("001000", msg.getFieldValueAsString(FIELD_3));
        Assert.assertEquals("000000001000", msg.getFieldValueAsString(FIELD_4));
        Assert.assertEquals("1004100741", msg.getFieldValueAsString(FIELD_7));
        Assert.assertEquals("732676", msg.getFieldValueAsString(FIELD_11));
        Assert.assertEquals("120741", msg.getFieldValueAsString(FIELD_12));
        Assert.assertEquals("1004", msg.getFieldValueAsString(FIELD_13));
        Assert.assertEquals("2108", msg.getFieldValueAsString(FIELD_14));
        Assert.assertEquals("5411", msg.getFieldValueAsString(FIELD_18));
        Assert.assertEquals("901", msg.getFieldValueAsString(FIELD_22));
        Assert.assertEquals("00", msg.getFieldValueAsString(FIELD_25));
        Assert.assertEquals("06", msg.getFieldValueAsString(FIELD_26));

        Assert.assertEquals("00123456789", msg.getFieldValueAsString(FIELD_32));
        Assert.assertEquals("5036163302452951=21081200000013011000", msg.getFieldValueAsString(FIELD_35));
        Assert.assertEquals("121004121020", msg.getFieldValueAsString(FIELD_37));

        Assert.assertEquals("480524", msg.getFieldValueAsString(FIELD_38));
        Assert.assertEquals("00", msg.getFieldValueAsString(FIELD_39));

        Assert.assertEquals("120", msg.getFieldValueAsString(FIELD_40));
        Assert.assertEquals("00000001", msg.getFieldValueAsString(FIELD_41));
        Assert.assertEquals("157000300000001", msg.getFieldValueAsString(FIELD_42));
        Assert.assertEquals("GENERAL MERCHANT                    WCZA", msg.getFieldValueAsString(FIELD_43));
        Assert.assertEquals("710", msg.getFieldValueAsString(FIELD_49));

        Assert.assertEquals("306B9B09520799F0", msg.getFieldValueAsString(FIELD_52));
        Assert.assertEquals("2057059000E0002F00000000000000000000000000000000000000000000000000000000000000000000000000000000", msg.getFieldValueAsString(FIELD_53));

        Assert.assertEquals("4.01", msg.getFieldValueAsString(FIELD_129));

        Assert.assertEquals("01", msg.getFieldValueAsString("130.0"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.1"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.2"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.3"));
        Assert.assertEquals("12", msg.getFieldValueAsString("130.4"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.5"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.6"));
        Assert.assertEquals("4", msg.getFieldValueAsString("130.7"));

        Assert.assertEquals("1", msg.getFieldValueAsString("131.0"));
        Assert.assertEquals("5", msg.getFieldValueAsString("131.1"));
        Assert.assertEquals("5", msg.getFieldValueAsString("131.2"));
        Assert.assertEquals("3", msg.getFieldValueAsString("131.3"));

        Assert.assertEquals("0", msg.getFieldValueAsString("132.0"));
        Assert.assertEquals("1", msg.getFieldValueAsString("132.1"));
        Assert.assertEquals("1", msg.getFieldValueAsString("132.2"));

        Assert.assertEquals("80208964", msg.getFieldValueAsString("135"));
        Assert.assertEquals("b9611779-0d42-4f43-b388-cbc58168e6a7", msg.getFieldValueAsString("137"));
        Assert.assertEquals("2", msg.getFieldValueAsString("138"));

        Assert.assertEquals("08", msg.getFieldValueAsString("170.2"));
        Assert.assertEquals(lexicalXSDHexBinary, DatatypeConverter.printHexBinary(msg.encode()));
    }


    @Test
    public void testReversal0420() throws Exception {
        String lexicalXSDHexBinary = "30343230F23CC4952BE08500000000420000000231363437393035373130303436353336333630303230303030303030303030313234353931303033313235303032313039333131313235303032313030333134313231303039353534313930313030433030303030303030433030303030303030313135303430303331303031343337343739303537313030343635333633363D31343132313236303030303030303038353030305034356968443130393331313638313236363034363837393931303030303030303030303939393554657374204D65726368616E742031202020202020202057696E64686F656B202020202020204E4135313630323032303533353136433030303030303030303030303030343430323130323030313039333131313030333132353030323030303030303030303030303030303030303030303030303030303030303030303030303030303030303030303043303030303030303043303030303030303070C0000080400000303131303031323031343135323330313363313032643939342D323434372D333835352D383230652D38373936643739646137313830313332332E3031363133333738303330303130383233355465726D4170702E49534F3A5265636F6E63696C696174696F6E496E64696361746F723133313039323138506F7374696C696F6E3A4D657461446174613234313233355465726D4170702E49534F3A5265636F6E63696C696174696F6E496E64696361746F72313131303735003500335472616465726F6F7451412E50534F2E496E746572636F6E6E6563742E42616E6B526F7574657242616E6B57696E64686F656B0010000601A91E903030000602A91E9030303031";
        Iso8583Message msg = Iso8583MessageFactory.parse(DatatypeConverter.parseHexBinary(lexicalXSDHexBinary), AcquirerProtocol.TRADEROUTE, 0);
        Assert.assertEquals("0420", msg.getMessageType());
        Assert.assertEquals("4790571004653636", msg.getFieldValueAsString(FIELD_2));
        Assert.assertEquals("002000", msg.getFieldValueAsString(FIELD_3));
        Assert.assertEquals("000000012459", msg.getFieldValueAsString(FIELD_4));
        Assert.assertEquals("1003125002", msg.getFieldValueAsString(FIELD_7));
        Assert.assertEquals("109311", msg.getFieldValueAsString(FIELD_11));
        Assert.assertEquals("125002", msg.getFieldValueAsString(FIELD_12));
        Assert.assertEquals("1003", msg.getFieldValueAsString(FIELD_13));
        Assert.assertEquals("1412", msg.getFieldValueAsString(FIELD_14));
        Assert.assertEquals("1009", msg.getFieldValueAsString(FIELD_17));
        Assert.assertEquals("5541", msg.getFieldValueAsString(FIELD_18));
        Assert.assertEquals("901", msg.getFieldValueAsString(FIELD_22));
        Assert.assertEquals("00", msg.getFieldValueAsString(FIELD_25));

        Assert.assertEquals("C00000000", msg.getFieldValueAsString(FIELD_28));
        Assert.assertEquals("C00000000", msg.getFieldValueAsString(FIELD_30));

        Assert.assertEquals("50400310014", msg.getFieldValueAsString(FIELD_32));
        Assert.assertEquals("4790571004653636=14121260000000085000", msg.getFieldValueAsString(FIELD_35));
        Assert.assertEquals("P45ihD109311", msg.getFieldValueAsString(FIELD_37));

        Assert.assertEquals("68", msg.getFieldValueAsString(FIELD_39));

        Assert.assertEquals("126", msg.getFieldValueAsString(FIELD_40));
        Assert.assertEquals("60468799", msg.getFieldValueAsString(FIELD_41));
        Assert.assertEquals("100000000009995", msg.getFieldValueAsString(FIELD_42));
        Assert.assertEquals("Test Merchant 1        Windhoek       NA", msg.getFieldValueAsString(FIELD_43));
        Assert.assertEquals("516", msg.getFieldValueAsString(FIELD_49));

        Assert.assertEquals("2053516C000000000000", msg.getFieldValueAsString(FIELD_54));

        Assert.assertEquals("4021", msg.getFieldValueAsString(FIELD_56));

        Assert.assertEquals("020010931110031250020000000000000000000000", msg.getFieldValueAsString(FIELD_90));
        Assert.assertEquals("000000000000000000000000C00000000C00000000", msg.getFieldValueAsString(FIELD_95));


        Assert.assertEquals("01", msg.getFieldValueAsString("130.0"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.1"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.2"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.3"));
        Assert.assertEquals("12", msg.getFieldValueAsString("130.4"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.5"));
        Assert.assertEquals("1", msg.getFieldValueAsString("130.6"));
        Assert.assertEquals("4", msg.getFieldValueAsString("130.7"));

        Assert.assertEquals("1", msg.getFieldValueAsString("131.0"));
        Assert.assertEquals("5", msg.getFieldValueAsString("131.1"));
        Assert.assertEquals("2", msg.getFieldValueAsString("131.2"));
        Assert.assertEquals("3", msg.getFieldValueAsString("131.3"));

        Assert.assertEquals("0", msg.getFieldValueAsString("132.0"));
        Assert.assertEquals("1", msg.getFieldValueAsString("132.1"));
        Assert.assertEquals("3", msg.getFieldValueAsString("132.2"));

        Assert.assertEquals("c102d994-2447-3855-820e-8796d79da718", msg.getFieldValueAsString("137"));
        Assert.assertEquals("23.0161337803", msg.getFieldValueAsString("138"));

        Assert.assertEquals("235TermApp.ISO:ReconciliationIndicator13109218Postilion:MetaData241235TermApp.ISO:ReconciliationIndicator111", msg.getFieldValueAsString("161"));

        Assert.assertEquals("01", msg.getFieldValueAsString("170.2"));
        Assert.assertEquals(lexicalXSDHexBinary, DatatypeConverter.printHexBinary(msg.encode()));
    }

    //The documentation for this test seems to be incorrect Hence all the commented out code
    @Test
    public void testReversalResponse0430() throws Exception {
        String lexicalXSDHexBinary = "30343330B22044012AC0800000000040100000023030323030303030303030303031323435393130303930383336323531303933313134353232393031313135323630303331303030333337343739303537313030343635333633363D3134313231323630303030303030303835303030503435696844313039333131303036303436383739393130303030303030303030393939353531363032303030303030303031303033313235303032303030303030303030303030303030303030303030303131353236303133313030303370C0060000400000303130323030313030303135303030303063313032643939342D323434372D333835352D383230652D38373936643739646137313830303136303030380004000000000000303033083030323136009200335472616465726F6F7451412E50534F2E496E746572636F6E6E6563742E42616E6B526F7574657242616E6B57696E64686F656B00215472616465726F6F7451412E50534F2E5365637572652E4361726453776974636800385472616465726F6F7451412E50534F2E496E746572636F6E6E6563742E42616E6B526F7574657246697273744E6174696F6E616C42616E6B0040000601A91E903030000602A91E903030000601A91E903031000602A91E903031000601A91EA03032000602A91EA03032000601A91F8A3032000602A91F9A30323037";
        Iso8583Message msg = Iso8583MessageFactory.parse(DatatypeConverter.parseHexBinary(lexicalXSDHexBinary), AcquirerProtocol.TRADEROUTE, 0);
        System.out.println(print(msg));
        Assert.assertEquals("0430", msg.getMessageType());
        Assert.assertEquals("002000", msg.getFieldValueAsString(FIELD_3));
        Assert.assertEquals("000000012459", msg.getFieldValueAsString(FIELD_4));
        Assert.assertEquals("1009083625", msg.getFieldValueAsString(FIELD_7));
        Assert.assertEquals("109311", msg.getFieldValueAsString(FIELD_11));
//        Assert.assertEquals("125002", msg.getFieldValueAsString(FIELD_12));
//        Assert.assertEquals("1003", msg.getFieldValueAsString(FIELD_13));
//        Assert.assertEquals("1009", msg.getFieldValueAsString(FIELD_17));
        Assert.assertEquals("4522", msg.getFieldValueAsString(FIELD_18));
        Assert.assertEquals("901", msg.getFieldValueAsString(FIELD_22));
//        Assert.assertEquals("00", msg.getFieldValueAsString(FIELD_25));

//        Assert.assertEquals("C00000000", msg.getFieldValueAsString(FIELD_28));
//        Assert.assertEquals("C00000000", msg.getFieldValueAsString(FIELD_30));

        Assert.assertEquals("52600310003", msg.getFieldValueAsString(FIELD_32));
        Assert.assertEquals("4790571004653636=14121260000000085000", msg.getFieldValueAsString(FIELD_35));
        Assert.assertEquals("P45ihD109311", msg.getFieldValueAsString(FIELD_37));

        Assert.assertEquals("00", msg.getFieldValueAsString(FIELD_39));

        Assert.assertEquals("60468799", msg.getFieldValueAsString(FIELD_41));
        Assert.assertEquals("100000000009995", msg.getFieldValueAsString(FIELD_42));
//        Assert.assertEquals("Test Merchant 1        Windhoek       NA", msg.getFieldValueAsString(FIELD_43));
        Assert.assertEquals("516", msg.getFieldValueAsString(FIELD_49));

//        Assert.assertEquals("2053516C000000000000", msg.getFieldValueAsString(FIELD_54));

//        Assert.assertEquals("4021", msg.getFieldValueAsString(FIELD_56));

        Assert.assertEquals("020000000010031250020000000000000000000000", msg.getFieldValueAsString(FIELD_90));
//        Assert.assertEquals("000000000000000000000000C00000000C00000000", msg.getFieldValueAsString(FIELD_95));

        Assert.assertEquals("52601310003", msg.getFieldValueAsString(FIELD_100));

        Assert.assertEquals("01", msg.getFieldValueAsString("130.0"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.1"));
        Assert.assertEquals("2", msg.getFieldValueAsString("130.2"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.3"));
        Assert.assertEquals("01", msg.getFieldValueAsString("130.4"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.5"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.6"));
        Assert.assertEquals("0", msg.getFieldValueAsString("130.7"));

        Assert.assertEquals("1", msg.getFieldValueAsString("131.0"));
        Assert.assertEquals("5", msg.getFieldValueAsString("131.1"));
        Assert.assertEquals("0", msg.getFieldValueAsString("131.2"));
        Assert.assertEquals("0", msg.getFieldValueAsString("131.3"));

        Assert.assertEquals("0", msg.getFieldValueAsString("132.0"));
        Assert.assertEquals("0", msg.getFieldValueAsString("132.1"));
        Assert.assertEquals("0", msg.getFieldValueAsString("132.2"));

        Assert.assertEquals("c102d994-2447-3855-820e-8796d79da718", msg.getFieldValueAsString("137"));
        Assert.assertEquals("6", msg.getFieldValueAsString("138"));

        Assert.assertEquals("000000", msg.getFieldValueAsString("150.14"));
        Assert.assertEquals("00", msg.getFieldValueAsString("151.5"));

//        Assert.assertEquals("235TermApp.ISO:ReconciliationIndicator13109218Postilion:MetaData241235TermApp.ISO:ReconciliationIndicator111", msg.getFieldValueAsString("161"));

        Assert.assertEquals("07", msg.getFieldValueAsString("170.2"));
        Assert.assertEquals(lexicalXSDHexBinary, DatatypeConverter.printHexBinary(msg.encode()));
    }


    public String print(Iso8583Message isoMessage) {
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("---- ISO MESSAGE -----\n");
            builder.append("  MTI : ").append(isoMessage.getMessageType());
            builder.append("\n");
            appendFields(builder, isoMessage);
        } catch (Iso8583Exception e) {
            e.printStackTrace();
        } finally {
            builder.append("----------------------");
        }
        return builder.toString();
    }

    private void appendFields(StringBuilder builder, Iso8583Message msg) throws Iso8583Exception {
        for (int i = 0; i <= msg.getMaxField(); i++) {
            if (msg.hasField(i)) {
                Object fieldValue = msg.getFieldValue(i);
                if (fieldValue instanceof Iso8583Message) {
                    Iso8583Message isoField = (Iso8583Message) fieldValue;
                    for (int j = 0; j <= isoField.getMaxField(); j++) {
                        if (isoField.hasField(j)) {
                            Object isoFieldValue = isoField.getFieldValue(j);
                            String fieldString = "    Field-" + String.format("%03d", i) + "." + j;
                            builder.append(String.format("%-16s", fieldString))
                                    .append(" : ")
                                    .append(String.format("%-45s", isoField.getFieldDescription(j)))
                                    .append(" : ");
                            if (isoFieldValue instanceof String) {
                                builder.append(isoField.getFieldValueAsString(j));
                            }
                            if (isoFieldValue instanceof byte[]) {
                                builder.append(DatatypeConverter.printHexBinary((byte[]) isoField.getFieldValue(j))).append(" (Binary Data)");
                            }
                            if (isoField.getFieldValueAsString(j) == null) {
                                builder.append(isoField.getFieldValue(j));
                            }
                            builder.append("\n");
                        }
                    }
                } else {
                    String fieldString = "    Field-" + String.format("%03d", i);
                    builder.append(String.format("%-16s", fieldString))
                            .append(" : ")
                            .append(String.format("%-45s", msg.getFieldDescription(i)))
                            .append(" : ");
                    if (fieldValue instanceof String) {
                        builder.append(msg.getFieldValueAsString(i));
                    }
                    if (msg.getFieldValueAsString(i) == null) {
                        builder.append(msg.getFieldValue(i));
                    }
                    if (fieldValue instanceof byte[]) {
                        builder.append(DatatypeConverter.printHexBinary(((byte[]) msg.getFieldValue(i)))).append(" (Binary Data)");
                    }
                    builder.append("\n");
                }

            }
        }
    }
}