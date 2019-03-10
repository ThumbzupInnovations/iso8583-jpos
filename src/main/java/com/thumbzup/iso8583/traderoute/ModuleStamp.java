package com.thumbzup.iso8583.traderoute;

import org.bouncycastle.util.Arrays;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class ModuleStamp implements Encodable {
    public enum Direction {
        RX((byte) 1), TX((byte) 2);

        private byte[] data = new byte[1];

        Direction(byte value) {
            data[0] = value;
        }

        public byte[] getBytes() {
            return data;
        }
    }

    private final byte[] messageTransitDirectionData;
    private final byte[] messageTransitTimeStampData;
    private final byte[] modulePublicNameIndexData;

    private final Direction messageTransitDirection;
    private final int messageTransitTimeStamp;
    private final String modulePublicNameIndex;

    public ModuleStamp(byte[] data) {
        messageTransitDirectionData = Arrays.copyOfRange(data, 0, 1); //length 1
        messageTransitTimeStampData = Arrays.copyOfRange(data, 1, 4); //length 3
        modulePublicNameIndexData = Arrays.copyOfRange(data, 4, 6); //length 2
        messageTransitDirection = messageTransitDirectionData[0] == 1 ? Direction.RX : Direction.TX;
        messageTransitTimeStamp = new BigInteger(messageTransitTimeStampData).intValue();
        modulePublicNameIndex = new String(modulePublicNameIndexData, StandardCharsets.ISO_8859_1);
    }

    public ModuleStamp(Direction messageTransitDirection, int messageTransitTimeStamp, String modulePublicNameIndex) {
        this.messageTransitDirection = messageTransitDirection;
        this.messageTransitTimeStamp = messageTransitTimeStamp;
        this.modulePublicNameIndex = modulePublicNameIndex;
        this.messageTransitDirectionData = messageTransitDirection.getBytes();
        this.messageTransitTimeStampData = Arrays.copyOf(BigInteger.valueOf(messageTransitTimeStamp).toByteArray(), 3);
        this.modulePublicNameIndexData = Arrays.copyOf(modulePublicNameIndex.getBytes(StandardCharsets.ISO_8859_1), 2);

    }

    public Direction getMessageTransitDirection() {
        return messageTransitDirection;
    }

    public int getMessageTransitTimeStamp() {
        return messageTransitTimeStamp;
    }

    public String getModulePublicNameIndex() {
        return modulePublicNameIndex;
    }

    @Override
    public byte[] getEncoded() {
        return Arrays.concatenate(messageTransitDirectionData, messageTransitTimeStampData, modulePublicNameIndexData);
    }
}
