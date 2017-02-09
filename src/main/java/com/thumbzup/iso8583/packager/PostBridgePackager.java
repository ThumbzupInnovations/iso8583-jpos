/*
 *     Copyright (C) 2017 Sean
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

package com.thumbzup.iso8583.packager;

import com.thumbzup.iso8583.FieldConstants;
import org.jpos.iso.*;

import java.util.HashMap;
import java.util.Map;


public class PostBridgePackager extends SimpleBasePackager {

    private final static Map<Integer, ISOFieldPackager> fieldMap = new HashMap<>();
    private final static ISOFieldPackager[] fields = new ISOFieldPackager[128 + 1];

    static {
        fieldMap.put(FieldConstants.FIELD_0, new IFA_NUMERIC(4, "MESSAGE TYPE INDICATOR"));
        fieldMap.put(FieldConstants.FIELD_1, new IFB_BITMAP(16, "BIT MAP"));
        fieldMap.put(FieldConstants.FIELD_2, new IFA_LLNUM(19, "PAN - PRIMARY ACCOUNT NUMBER"));
        fieldMap.put(FieldConstants.FIELD_3, new IFA_NUMERIC(6, "PROCESSING CODE"));
        fieldMap.put(FieldConstants.FIELD_4, new IFA_NUMERIC(12, "AMOUNT, TRANSACTION"));
        fieldMap.put(FieldConstants.FIELD_5, new IFA_NUMERIC(12, "AMOUNT, SETTLEMENT"));
        fieldMap.put(FieldConstants.FIELD_6, new IFA_NUMERIC(12, "AMOUNT, CARDHOLDER BILLING"));
        fieldMap.put(FieldConstants.FIELD_7, new IFA_NUMERIC(10, "TRANSMISSION DATE AND TIME"));
        fieldMap.put(FieldConstants.FIELD_8, new IFA_NUMERIC(8, "AMOUNT, CARDHOLDER BILLING FEE"));
        fieldMap.put(FieldConstants.FIELD_9, new IFA_NUMERIC(8, "CONVERSION RATE, SETTLEMENT"));
        fieldMap.put(FieldConstants.FIELD_10, new IFA_NUMERIC(8, "CONVERSION RATE, CARDHOLDER BILLING"));
        fieldMap.put(FieldConstants.FIELD_11, new IFA_NUMERIC(6, "SYSTEM TRACE AUDIT NUMBER"));
        fieldMap.put(FieldConstants.FIELD_12, new IFA_NUMERIC(6, "TIME, LOCAL TRANSACTION"));
        fieldMap.put(FieldConstants.FIELD_13, new IFA_NUMERIC(4, "DATE, LOCAL TRANSACTION"));
        fieldMap.put(FieldConstants.FIELD_14, new IFA_NUMERIC(4, "DATE, EXPIRATION"));
        fieldMap.put(FieldConstants.FIELD_15, new IFA_NUMERIC(4, "DATE, SETTLEMENT"));
        fieldMap.put(FieldConstants.FIELD_16, new IFA_NUMERIC(4, "DATE, CONVERSION"));
        fieldMap.put(FieldConstants.FIELD_17, new IFA_NUMERIC(4, "DATE, CAPTURE"));
        fieldMap.put(FieldConstants.FIELD_18, new IFA_NUMERIC(4, "MERCHANTS TYPE"));
        fieldMap.put(FieldConstants.FIELD_19, new IFA_NUMERIC(3, "ACQUIRING INSTITUTION COUNTRY CODE"));
        fieldMap.put(FieldConstants.FIELD_20, new IFA_NUMERIC(3, "PAN EXTENDED COUNTRY CODE"));
        fieldMap.put(FieldConstants.FIELD_21, new IFA_NUMERIC(3, "FORWARDING INSTITUTION COUNTRY CODE"));
        fieldMap.put(FieldConstants.FIELD_22, new IFA_NUMERIC(3, "POINT OF SERVICE ENTRY MODE"));
        fieldMap.put(FieldConstants.FIELD_23, new IFA_NUMERIC(3, "CARD SEQUENCE NUMBER"));
        fieldMap.put(FieldConstants.FIELD_24, new IFA_NUMERIC(3, "NETWORK INTERNATIONAL IDENTIFIEER"));
        fieldMap.put(FieldConstants.FIELD_25, new IFA_NUMERIC(2, "POINT OF SERVICE CONDITION CODE"));
        fieldMap.put(FieldConstants.FIELD_26, new IFA_NUMERIC(2, "POINT OF SERVICE PIN CAPTURE CODE"));
        fieldMap.put(FieldConstants.FIELD_27, new IFA_NUMERIC(1, "AUTHORIZATION IDENTIFICATION RESP LEN"));
        fieldMap.put(FieldConstants.FIELD_28, new IFA_AMOUNT(9, "AMOUNT, TRANSACTION FEE"));
        fieldMap.put(FieldConstants.FIELD_29, new IFA_AMOUNT(9, "AMOUNT, SETTLEMENT FEE"));
        fieldMap.put(FieldConstants.FIELD_30, new IFA_AMOUNT(9, "AMOUNT, TRANSACTION PROCESSING FEE"));
        fieldMap.put(FieldConstants.FIELD_31, new IFA_AMOUNT(9, "AMOUNT, SETTLEMENT PROCESSING FEE"));
        fieldMap.put(FieldConstants.FIELD_32, new IFA_LLNUM(11, "ACQUIRING INSTITUTION IDENT CODE"));
        fieldMap.put(FieldConstants.FIELD_33, new IFA_LLNUM(11, "FORWARDING INSTITUTION IDENT CODE"));
        fieldMap.put(FieldConstants.FIELD_34, new IFA_LLCHAR(28, "PAN EXTENDED"));
        fieldMap.put(FieldConstants.FIELD_35, new IFA_LLNUM(37, "TRACK 2 DATA"));
        fieldMap.put(FieldConstants.FIELD_36, new IFA_LLLCHAR(104, "TRACK 3 DATA"));
        fieldMap.put(FieldConstants.FIELD_37, new IF_CHAR(12, "RETRIEVAL REFERENCE NUMBER"));
        fieldMap.put(FieldConstants.FIELD_38, new IF_CHAR(6, "AUTHORIZATION IDENTIFICATION RESPONSE"));
        fieldMap.put(FieldConstants.FIELD_39, new IF_CHAR(2, "RESPONSE CODE"));
        fieldMap.put(FieldConstants.FIELD_40, new IF_CHAR(3, "SERVICE RESTRICTION CODE"));
        fieldMap.put(FieldConstants.FIELD_41, new IF_CHAR(8, "CARD ACCEPTOR TERMINAL IDENTIFICACION"));
        fieldMap.put(FieldConstants.FIELD_42, new IF_CHAR(15, "CARD ACCEPTOR IDENTIFICATION CODE"));
        fieldMap.put(FieldConstants.FIELD_43, new IF_CHAR(40, "CARD ACCEPTOR NAME/LOCATION"));
        fieldMap.put(FieldConstants.FIELD_44, new IFA_LLCHAR(25, "ADITIONAL RESPONSE DATA"));
        fieldMap.put(FieldConstants.FIELD_45, new IFA_LLCHAR(76, "TRACK 1 DATA"));
        fieldMap.put(FieldConstants.FIELD_46, new IFA_LLLCHAR(999, "ADITIONAL DATA - ISO"));
        fieldMap.put(FieldConstants.FIELD_47, new IFA_LLLCHAR(999, "ADITIONAL DATA - NATIONAL"));
        fieldMap.put(FieldConstants.FIELD_48, new IFA_LLLCHAR(999, "ADITIONAL DATA - PRIVATE"));
        fieldMap.put(FieldConstants.FIELD_49, new IF_CHAR(3, "CURRENCY CODE, TRANSACTION"));
        fieldMap.put(FieldConstants.FIELD_50, new IF_CHAR(3, "CURRENCY CODE, SETTLEMENT"));
        fieldMap.put(FieldConstants.FIELD_51, new IF_CHAR(3, "CURRENCY CODE, CARDHOLDER BILLING"));
        fieldMap.put(FieldConstants.FIELD_52, new IFB_BINARY(8, "PIN DATA"));
        fieldMap.put(FieldConstants.FIELD_53, new IFB_BINARY(48, "SECURITY RELATED CONTROL INFORMATION"));
        fieldMap.put(FieldConstants.FIELD_54, new IFA_LLLCHAR(120, "ADDITIONAL AMOUNTS"));
        fieldMap.put(FieldConstants.FIELD_55, new IFA_LLLCHAR(999, "RESERVED ISO"));
        fieldMap.put(FieldConstants.FIELD_56, new IFA_LLLCHAR(999, "RESERVED ISO"));
        fieldMap.put(FieldConstants.FIELD_57, new IFA_LLLCHAR(999, "RESERVED NATIONAL"));
        fieldMap.put(FieldConstants.FIELD_58, new IFA_LLLCHAR(999, "RESERVED NATIONAL"));
        fieldMap.put(FieldConstants.FIELD_59, new IFA_LLLCHAR(999, "RESERVED NATIONAL"));
        fieldMap.put(FieldConstants.FIELD_60, new IFA_LLLCHAR(999, "RESERVED PRIVATE"));
        fieldMap.put(FieldConstants.FIELD_61, new IFA_LLLCHAR(999, "RESERVED PRIVATE"));
        fieldMap.put(FieldConstants.FIELD_62, new IFA_LLLCHAR(999, "RESERVED PRIVATE"));
        fieldMap.put(FieldConstants.FIELD_63, new IFA_LLLCHAR(999, "RESERVED PRIVATE"));
        fieldMap.put(FieldConstants.FIELD_64, new IFA_BINARY(8, "MESSAGE AUTHENTICATION CODE FIELD"));
        fieldMap.put(FieldConstants.FIELD_65, new IFA_BINARY(8, "BITMAP, EXTENDED"));
        fieldMap.put(FieldConstants.FIELD_66, new IFA_NUMERIC(1, "SETTLEMENT CODE"));
        fieldMap.put(FieldConstants.FIELD_67, new IFA_NUMERIC(2, "EXTENDED PAYMENT CODE"));
        fieldMap.put(FieldConstants.FIELD_68, new IFA_NUMERIC(3, "RECEIVING INSTITUTION COUNTRY CODE"));
        fieldMap.put(FieldConstants.FIELD_69, new IFA_NUMERIC(3, "SETTLEMENT INSTITUTION COUNTRY CODE"));
        fieldMap.put(FieldConstants.FIELD_70, new IFA_NUMERIC(3, "NETWORK MANAGEMENT INFORMATION CODE"));
        fieldMap.put(FieldConstants.FIELD_71, new IFA_NUMERIC(4, "MESSAGE NUMBER"));
        fieldMap.put(FieldConstants.FIELD_72, new IFA_NUMERIC(4, "MESSAGE NUMBER LAST"));
        fieldMap.put(FieldConstants.FIELD_73, new IFA_NUMERIC(6, "DATE ACTION"));
        fieldMap.put(FieldConstants.FIELD_74, new IFA_NUMERIC(10, "CREDITS NUMBER"));
        fieldMap.put(FieldConstants.FIELD_75, new IFA_NUMERIC(10, "CREDITS REVERSAL NUMBER"));
        fieldMap.put(FieldConstants.FIELD_76, new IFA_NUMERIC(10, "DEBITS NUMBER"));
        fieldMap.put(FieldConstants.FIELD_77, new IFA_NUMERIC(10, "DEBITS REVERSAL NUMBER"));
        fieldMap.put(FieldConstants.FIELD_78, new IFA_NUMERIC(10, "TRANSFER NUMBER"));
        fieldMap.put(FieldConstants.FIELD_79, new IFA_NUMERIC(10, "TRANSFER REVERSAL NUMBER"));
        fieldMap.put(FieldConstants.FIELD_80, new IFA_NUMERIC(10, "INQUIRIES NUMBER"));
        fieldMap.put(FieldConstants.FIELD_81, new IFA_NUMERIC(10, "AUTHORIZATION NUMBER"));
        fieldMap.put(FieldConstants.FIELD_82, new IFA_NUMERIC(12, "CREDITS, PROCESSING FEE AMOUNT"));
        fieldMap.put(FieldConstants.FIELD_83, new IFA_NUMERIC(12, "CREDITS, TRANSACTION FEE AMOUNT"));
        fieldMap.put(FieldConstants.FIELD_84, new IFA_NUMERIC(12, "DEBITS, PROCESSING FEE AMOUNT"));
        fieldMap.put(FieldConstants.FIELD_85, new IFA_NUMERIC(12, "DEBITS, TRANSACTION FEE AMOUNT"));
        fieldMap.put(FieldConstants.FIELD_86, new IFA_NUMERIC(16, "CREDITS, AMOUNT"));
        fieldMap.put(FieldConstants.FIELD_87, new IFA_NUMERIC(16, "CREDITS, REVERSAL AMOUNT"));
        fieldMap.put(FieldConstants.FIELD_88, new IFA_NUMERIC(16, "DEBITS, AMOUNT"));
        fieldMap.put(FieldConstants.FIELD_89, new IFA_NUMERIC(16, "DEBITS, REVERSAL AMOUNT"));
        fieldMap.put(FieldConstants.FIELD_90, new IFA_NUMERIC(42, "ORIGINAL DATA ELEMENTS"));
        fieldMap.put(FieldConstants.FIELD_91, new IF_CHAR(1, "FILE UPDATE CODE"));
        fieldMap.put(FieldConstants.FIELD_92, new IF_CHAR(2, "FILE SECURITY CODE"));
        fieldMap.put(FieldConstants.FIELD_93, new IF_CHAR(5, "RESPONSE INDICATOR"));
        fieldMap.put(FieldConstants.FIELD_94, new IF_CHAR(7, "SERVICE INDICATOR"));
        fieldMap.put(FieldConstants.FIELD_95, new IF_CHAR(42, "REPLACEMENT AMOUNTS"));
        fieldMap.put(FieldConstants.FIELD_96, new IFA_BINARY(8, "MESSAGE SECURITY CODE"));
        fieldMap.put(FieldConstants.FIELD_97, new IFA_AMOUNT(17, "AMOUNT, NET SETTLEMENT"));
        fieldMap.put(FieldConstants.FIELD_98, new IF_CHAR(25, "PAYEE"));
        fieldMap.put(FieldConstants.FIELD_99, new IFA_LLNUM(11, "SETTLEMENT INSTITUTION IDENT CODE"));
        fieldMap.put(FieldConstants.FIELD_100, new IFA_LLNUM(11, "RECEIVING INSTITUTION IDENT CODE"));
        fieldMap.put(FieldConstants.FIELD_101, new IFA_LLCHAR(17, "FILE NAME"));
        fieldMap.put(FieldConstants.FIELD_102, new IFA_LLCHAR(28, "ACCOUNT IDENTIFICATION 1"));
        fieldMap.put(FieldConstants.FIELD_103, new IFA_LLCHAR(28, "ACCOUNT IDENTIFICATION 2"));
        fieldMap.put(FieldConstants.FIELD_104, new IFA_LLLCHAR(100, "TRANSACTION DESCRIPTION"));
        fieldMap.put(FieldConstants.FIELD_105, new IFA_LLLCHAR(999, "RESERVED ISO USE"));
        fieldMap.put(FieldConstants.FIELD_106, new IFA_LLLCHAR(999, "RESERVED ISO USE"));
        fieldMap.put(FieldConstants.FIELD_107, new IFA_LLLCHAR(999, "RESERVED ISO USE"));
        fieldMap.put(FieldConstants.FIELD_108, new IFA_LLLCHAR(999, "RESERVED ISO USE"));
        fieldMap.put(FieldConstants.FIELD_109, new IFA_LLLCHAR(999, "RESERVED ISO USE"));
        fieldMap.put(FieldConstants.FIELD_110, new IFA_LLLCHAR(999, "RESERVED ISO USE"));
        fieldMap.put(FieldConstants.FIELD_111, new IFA_LLLCHAR(999, "RESERVED ISO USE"));
        fieldMap.put(FieldConstants.FIELD_112, new IFA_LLLCHAR(999, "RESERVED NATIONAL USE"));
        fieldMap.put(FieldConstants.FIELD_113, new IFA_LLLCHAR(999, "RESERVED NATIONAL USE"));
        fieldMap.put(FieldConstants.FIELD_114, new IFA_LLLCHAR(999, "RESERVED NATIONAL USE"));
        fieldMap.put(FieldConstants.FIELD_115, new IFA_LLLCHAR(999, "RESERVED NATIONAL USE"));
        fieldMap.put(FieldConstants.FIELD_116, new IFA_LLLCHAR(999, "RESERVED NATIONAL USE"));
        fieldMap.put(FieldConstants.FIELD_117, new IFA_LLLCHAR(999, "RESERVED NATIONAL USE"));
        fieldMap.put(FieldConstants.FIELD_118, new IFA_LLLCHAR(999, "RESERVED NATIONAL USE"));
        fieldMap.put(FieldConstants.FIELD_119, new IFA_LLLCHAR(999, "RESERVED NATIONAL USE"));
        fieldMap.put(FieldConstants.FIELD_120, new IFA_LLLCHAR(999, "RESERVED PRIVATE USE"));
        fieldMap.put(FieldConstants.FIELD_121, new IFA_LLLCHAR(999, "RESERVED PRIVATE USE"));
        fieldMap.put(FieldConstants.FIELD_122, new IFA_LLLCHAR(999, "RESERVED PRIVATE USE"));
        fieldMap.put(FieldConstants.FIELD_123, new IFA_LLLCHAR(999, "RESERVED PRIVATE USE"));
        fieldMap.put(FieldConstants.FIELD_124, new IFA_LLLCHAR(999, "RESERVED PRIVATE USE"));
        fieldMap.put(FieldConstants.FIELD_125, new IFA_LLLCHAR(999, "NETWORK MANAGEMENT INFORMATION"));
        fieldMap.put(FieldConstants.FIELD_126, new IFA_LLLCHAR(999, "RESERVED PRIVATE USE"));
        fieldMap.put(FieldConstants.FIELD_127, new ISOMsgFieldPackager(new IFA_LLLLLLBINARY(99999, "RESERVED PRIVATE USE"), new PostBridge127Packager()));
        fieldMap.put(FieldConstants.FIELD_128, new IFA_LLLCHAR(999, "MAC 2"));
        for (int i = 0; i <= fields.length; i++) {
            if (fieldMap.containsKey(i)) {
                fields[i] = fieldMap.get(i);
            }
        }
    }

    public PostBridgePackager() {
        super();
        setFieldPackager(fields);
    }

}
