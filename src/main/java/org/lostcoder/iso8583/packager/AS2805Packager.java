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

package org.lostcoder.iso8583.packager;

import org.jpos.iso.*;
import org.lostcoder.iso8583.FieldConstants;

import java.util.HashMap;
import java.util.Map;

public class AS2805Packager extends AS2805BasePackager {

    private final static Map<Integer, ISOFieldPackager> fieldMap = new HashMap<>();
    private final static ISOFieldPackager[] fields = new ISOFieldPackager[128 + 1];
    private static final boolean pad = true;

    static {
        fieldMap.put(FieldConstants.FIELD_0, new IFB_NUMERIC(4, "Message Type Indicator", pad));
        fieldMap.put(FieldConstants.FIELD_1, new IFB_BITMAP(16, "Bitmap, Extended"));
        fieldMap.put(FieldConstants.FIELD_2, new IFB_LLNUM(19, "Primary Account Number", !pad));
        fieldMap.put(FieldConstants.FIELD_3, new IFB_NUMERIC(6, "Processing Code", pad));
        fieldMap.put(FieldConstants.FIELD_4, new IFB_NUMERIC(12, "Amount, Transaction", pad));
        fieldMap.put(FieldConstants.FIELD_7, new IFB_NUMERIC(10, "Transmission Date and Time", pad));
        fieldMap.put(FieldConstants.FIELD_11, new IFB_NUMERIC(6, "Systems Trace Audit Number (STAN)", pad));
        fieldMap.put(FieldConstants.FIELD_12, new IFB_NUMERIC(6, "Time, Local Transaction", pad));
        fieldMap.put(FieldConstants.FIELD_13, new IFB_NUMERIC(4, "Date, Local Transaction", pad));
        fieldMap.put(FieldConstants.FIELD_14, new IFB_NUMERIC(4, "Date, Expiry", pad));
        fieldMap.put(FieldConstants.FIELD_15, new IFB_NUMERIC(4, "Date, Settlement", pad));
        fieldMap.put(FieldConstants.FIELD_18, new IFB_NUMERIC(4, "Merchant Type", pad));
        fieldMap.put(FieldConstants.FIELD_22, new IFB_NUMERIC(3, "Point of Service Entry Mode", pad));
        fieldMap.put(FieldConstants.FIELD_23, new IFB_NUMERIC(3, "Card Sequence Number", pad));
        fieldMap.put(FieldConstants.FIELD_25, new IFB_NUMERIC(2, "Point of Service Condition Code", pad));
        fieldMap.put(FieldConstants.FIELD_32, new IFB_LLNUM(11, "Acquiring Institution Identification Code", pad));
        fieldMap.put(FieldConstants.FIELD_33, new IFB_LLNUM(11, "Forwarding Institution Identification Code", pad));
        fieldMap.put(FieldConstants.FIELD_35, new IFB_LLNUM(37, "Track 2 Data", false, true));
        fieldMap.put(FieldConstants.FIELD_37, new IF_CHAR(12, "Retrieval Reference Number"));
        fieldMap.put(FieldConstants.FIELD_38, new IF_CHAR(6, "Authorisation Id Response"));
        fieldMap.put(FieldConstants.FIELD_39, new IF_CHAR(2, "Response Code"));
        fieldMap.put(FieldConstants.FIELD_41, new IF_CHAR(8, "Card Acceptor Terminal Identification"));
        fieldMap.put(FieldConstants.FIELD_42, new IF_CHAR(15, "Card Acceptor Identification Code"));
        fieldMap.put(FieldConstants.FIELD_43, new IF_CHAR(40, "Card Acceptor Name and Location"));
        fieldMap.put(FieldConstants.FIELD_47, new IFA_LLLCHAR(999, "Additional Data - National"));
        fieldMap.put(FieldConstants.FIELD_48, new IFA_LLLBINARY(999, "Additional Data - Private"));
        fieldMap.put(FieldConstants.FIELD_52, new IFB_BINARY(8, "Personal Identification Number (PIN) Data"));
        fieldMap.put(FieldConstants.FIELD_53, new IFB_NUMERIC(16, "Security Related Control Information", pad));
        fieldMap.put(FieldConstants.FIELD_55, new IFA_LLLBINARY(999, "EMV Chip Data"));
        fieldMap.put(FieldConstants.FIELD_57, new IFB_NUMERIC(12, "Amount, Cash", pad));
        fieldMap.put(FieldConstants.FIELD_58, new IFB_BINARY(6, "Ledger Balance"));
        fieldMap.put(FieldConstants.FIELD_59, new IFB_BINARY(6, "Account Balance"));
        fieldMap.put(FieldConstants.FIELD_64, new IFB_BINARY(8, "Message Authentication Code (MAC)"));
        fieldMap.put(FieldConstants.FIELD_70, new IFB_NUMERIC(3, "Network Management Information Code", pad));
        fieldMap.put(FieldConstants.FIELD_90, new IFB_NUMERIC(42, "Original Data Elements", pad));
        fieldMap.put(FieldConstants.FIELD_97, new IFB_AMOUNT(1 + 16, "Amount, Net reconciliation", pad));
        fieldMap.put(FieldConstants.FIELD_100, new IFB_LLNUM(11, "Receiving Institution Identification Code", pad));
        fieldMap.put(FieldConstants.FIELD_128, new IFB_BINARY(8, "Message Authentication Code (MAC)"));

        for (int i = 0; i <= fields.length; i++) {
            if (fieldMap.containsKey(i)) {
                fields[i] = fieldMap.get(i);
            }
        }
    }

    public AS2805Packager() {
        super();
        setFieldPackager(fields);
    }
}
