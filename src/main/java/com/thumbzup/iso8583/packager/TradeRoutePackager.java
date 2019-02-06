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


public class TradeRoutePackager extends ISOBasePackager {

    private final static Map<Integer, ISOFieldPackager> fieldMap = new HashMap<>();
    private final static ISOFieldPackager[] fields = new ISOFieldPackager[192 + 1];

    static {
        fieldMap.put(FieldConstants.FIELD_0, new IFA_NUMERIC(4, "Message Type Indicator"));
        fieldMap.put(FieldConstants.FIELD_1, new IFB_BITMAP(16, "Primary Bit Map"));
        fieldMap.put(FieldConstants.FIELD_2, new IFA_LLNUM(19, "Primary Account Number"));
        fieldMap.put(FieldConstants.FIELD_3, new IFA_NUMERIC(6, "Processing Code"));
        fieldMap.put(FieldConstants.FIELD_4, new IFA_NUMERIC(12, "Amount, Transaction"));
        fieldMap.put(FieldConstants.FIELD_7, new IFA_NUMERIC(10, "Transmission Date and Time"));
        fieldMap.put(FieldConstants.FIELD_11, new IFA_NUMERIC(6, "Systems Trace Audit Number"));
        fieldMap.put(FieldConstants.FIELD_12, new IFA_NUMERIC(6, "Time, Local Transaction"));
        fieldMap.put(FieldConstants.FIELD_13, new IFA_NUMERIC(4, "Date, Local Transaction"));
        fieldMap.put(FieldConstants.FIELD_14, new IFA_NUMERIC(4, "Date, Expiration"));
        fieldMap.put(FieldConstants.FIELD_15, new IFA_NUMERIC(4, "Date, Settlement"));
        fieldMap.put(FieldConstants.FIELD_17, new IFA_NUMERIC(4, "Date, Capture"));
        fieldMap.put(FieldConstants.FIELD_18, new IFA_NUMERIC(4, "Merchant Type"));
        fieldMap.put(FieldConstants.FIELD_22, new IFA_NUMERIC(3, "POS Entry Mode"));
        fieldMap.put(FieldConstants.FIELD_23, new IFA_NUMERIC(3, "Card Sequence Number"));
        fieldMap.put(FieldConstants.FIELD_25, new IFA_NUMERIC(2, "POS Condition Code"));
        fieldMap.put(FieldConstants.FIELD_26, new IFA_NUMERIC(2, "POS PIN Capture Code"));
        fieldMap.put(FieldConstants.FIELD_27, new IFA_NUMERIC(1, "Authorization ID Response Length"));
        fieldMap.put(FieldConstants.FIELD_28, new IFA_AMOUNT(9, "Amount, Transaction Fee"));
        fieldMap.put(FieldConstants.FIELD_30, new IFA_AMOUNT(9, "Amount, Transaction Processing Fee"));
        fieldMap.put(FieldConstants.FIELD_32, new IFA_LLNUM(11, "Acquiring Institution Id Code"));
        fieldMap.put(FieldConstants.FIELD_33, new IFA_LLNUM(11, "Forwarding Institution Id Code"));
        fieldMap.put(FieldConstants.FIELD_35, new IFA_LLNUM(37, "Track 2 Data"));
        fieldMap.put(FieldConstants.FIELD_37, new IF_CHAR(12, "Retrieval Reference Number"));
        fieldMap.put(FieldConstants.FIELD_38, new IF_CHAR(6, "Authorization ID Response"));
        fieldMap.put(FieldConstants.FIELD_39, new IF_CHAR(2, "Response Code"));
        fieldMap.put(FieldConstants.FIELD_40, new IF_CHAR(3, "Service Restriction Code"));
        fieldMap.put(FieldConstants.FIELD_41, new IF_CHAR(8, "Card Acceptor Terminal ID"));
        fieldMap.put(FieldConstants.FIELD_42, new IF_CHAR(15, "Card Acceptor Id Code"));
        fieldMap.put(FieldConstants.FIELD_43, new IF_CHAR(40, "Card Acceptor Name Location"));
        fieldMap.put(FieldConstants.FIELD_49, new IF_CHAR(3, "Currency Code, Transaction"));
        fieldMap.put(FieldConstants.FIELD_52, new IFB_BINARY(8, "Pin Data"));
        fieldMap.put(FieldConstants.FIELD_53, new IFB_BINARY(48, "Security Related Control Information"));
        fieldMap.put(FieldConstants.FIELD_54, new IFA_LLLCHAR(120, "Additional Amounts"));
        fieldMap.put(FieldConstants.FIELD_56, new IFA_LLLNUM(4, "Message Reason Code"));
        fieldMap.put(FieldConstants.FIELD_70, new IFA_NUMERIC(3, "Network Management Information Code"));
        fieldMap.put(FieldConstants.FIELD_90, new IFA_NUMERIC(42, "Original Data Elements"));
        fieldMap.put(FieldConstants.FIELD_95, new IF_CHAR(42, "Replacement Amounts"));
        fieldMap.put(FieldConstants.FIELD_100, new IFA_LLNUM(11, "Receiving Institution Identification Code"));
        fieldMap.put(FieldConstants.FIELD_127, new IFB_BITMAP(8, "Tertiary Bit Map"));
        fieldMap.put(FieldConstants.FIELD_129, new IFA_LLLCHAR(999, "Traderoot ISO Version"));
        fieldMap.put(FieldConstants.FIELD_130, new ISOMsgFieldPackager(new IF_CHAR(10, "Card Acceptor Terminal Profile"), new TradeRoute130Packager()));
        fieldMap.put(FieldConstants.FIELD_131, new ISOMsgFieldPackager(new IF_CHAR(4, "Card Profile"), new TradeRoute131Packager()));
        fieldMap.put(FieldConstants.FIELD_132, new ISOMsgFieldPackager(new IF_CHAR(3, "Cardholder Profile"), new TradeRoute132Packager()));
        fieldMap.put(FieldConstants.FIELD_135, new IFA_NUMERIC(8, "Session ID"));
        fieldMap.put(FieldConstants.FIELD_137, new IF_CHAR(36, "UUID"));
        fieldMap.put(FieldConstants.FIELD_138, new IFA_LLLCHAR(64, "Echo Data"));
        fieldMap.put(FieldConstants.FIELD_139, new IFA_LLLCHAR(255, "Response Code Text"));
        fieldMap.put(FieldConstants.FIELD_144, new IFA_LLNUM(19, "Primary Account Number, Sanitised"));
        fieldMap.put(FieldConstants.FIELD_150, new ISOMsgFieldPackager(new IFA_LLLLBINARY(9999, "EMV Data"), new TradeRoute150Packager()));
        fieldMap.put(FieldConstants.FIELD_151, new ISOMsgFieldPackager(new IFA_LLLBINARY(999, "3D-Secure Data"), new TradeRoute151Packager()));
//        fieldMap.put(FieldConstants.FIELD_150,  new IFA_LLLLBINARY(9999, "EMV Data"));
//        fieldMap.put(FieldConstants.FIELD_151, new IFA_LLLBINARY(999, "3D-Secure Data"));
        fieldMap.put(FieldConstants.FIELD_153, new ISOMsgFieldPackager(new IFA_LLLLLLCHAR(999999, "Additional Data Elements"), new TradeRoute153Packager()));
        fieldMap.put(FieldConstants.FIELD_161, new IFA_LLLLLCHAR(99999, "Structured Data"));
        fieldMap.put(FieldConstants.FIELD_170, new ISOMsgFieldPackager(new IFA_LLLBINARY(999, "Sub Message - Message Transit Data"), new TradeRoute170Packager()));
        fieldMap.put(FieldConstants.FIELD_172, new ISOMsgFieldPackager(new IF_LENGTH_BINARY(4, "Sub Message - Replacement Elements Extended"), new TradeRoute172Packager()));



        fieldMap.put(FieldConstants.FIELD_5, new IFA_NUMERIC(12, "AMOUNT, SETTLEMENT"));
        fieldMap.put(FieldConstants.FIELD_6, new IFA_NUMERIC(12, "AMOUNT, CARDHOLDER BILLING"));
        fieldMap.put(FieldConstants.FIELD_8, new IFA_NUMERIC(8, "AMOUNT, CARDHOLDER BILLING FEE"));
        fieldMap.put(FieldConstants.FIELD_9, new IFA_NUMERIC(8, "CONVERSION RATE, SETTLEMENT"));
        fieldMap.put(FieldConstants.FIELD_10, new IFA_NUMERIC(8, "CONVERSION RATE, CARDHOLDER BILLING"));
        fieldMap.put(FieldConstants.FIELD_16, new IFA_NUMERIC(4, "DATE, CONVERSION"));
        fieldMap.put(FieldConstants.FIELD_19, new IFA_NUMERIC(3, "ACQUIRING INSTITUTION COUNTRY CODE"));
        fieldMap.put(FieldConstants.FIELD_20, new IFA_NUMERIC(3, "PAN EXTENDED COUNTRY CODE"));
        fieldMap.put(FieldConstants.FIELD_21, new IFA_NUMERIC(3, "FORWARDING INSTITUTION COUNTRY CODE"));
        fieldMap.put(FieldConstants.FIELD_24, new IFA_NUMERIC(3, "NETWORK INTERNATIONAL IDENTIFIEER"));
        fieldMap.put(FieldConstants.FIELD_29, new IFA_AMOUNT(9, "AMOUNT, SETTLEMENT FEE"));
        fieldMap.put(FieldConstants.FIELD_31, new IFA_AMOUNT(9, "AMOUNT, SETTLEMENT PROCESSING FEE"));
        fieldMap.put(FieldConstants.FIELD_34, new IFA_LLCHAR(28, "PAN EXTENDED"));
        fieldMap.put(FieldConstants.FIELD_36, new IFA_LLLCHAR(104, "TRACK 3 DATA"));
        fieldMap.put(FieldConstants.FIELD_44, new IFA_LLCHAR(25, "ADITIONAL RESPONSE DATA"));
        fieldMap.put(FieldConstants.FIELD_45, new IFA_LLCHAR(76, "TRACK 1 DATA"));
        fieldMap.put(FieldConstants.FIELD_46, new IFA_LLLCHAR(999, "ADITIONAL DATA - ISO"));
        fieldMap.put(FieldConstants.FIELD_47, new IFA_LLLCHAR(999, "ADITIONAL DATA - NATIONAL"));
        fieldMap.put(FieldConstants.FIELD_48, new IFA_LLLCHAR(999, "ADITIONAL DATA - PRIVATE"));
        fieldMap.put(FieldConstants.FIELD_50, new IF_CHAR(3, "CURRENCY CODE, SETTLEMENT"));
        fieldMap.put(FieldConstants.FIELD_51, new IF_CHAR(3, "CURRENCY CODE, CARDHOLDER BILLING"));
        fieldMap.put(FieldConstants.FIELD_55, new IFA_LLLCHAR(999, "RESERVED ISO"));
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

        fieldMap.put(FieldConstants.FIELD_91, new IF_CHAR(1, "FILE UPDATE CODE"));
        fieldMap.put(FieldConstants.FIELD_92, new IF_CHAR(2, "FILE SECURITY CODE"));
        fieldMap.put(FieldConstants.FIELD_93, new IF_CHAR(5, "RESPONSE INDICATOR"));
        fieldMap.put(FieldConstants.FIELD_94, new IF_CHAR(7, "SERVICE INDICATOR"));

        fieldMap.put(FieldConstants.FIELD_96, new IFA_BINARY(8, "MESSAGE SECURITY CODE"));
        fieldMap.put(FieldConstants.FIELD_97, new IFA_AMOUNT(17, "AMOUNT, NET SETTLEMENT"));
        fieldMap.put(FieldConstants.FIELD_98, new IF_CHAR(25, "PAYEE"));
        fieldMap.put(FieldConstants.FIELD_99, new IFA_LLNUM(11, "SETTLEMENT INSTITUTION IDENT CODE"));

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
        fieldMap.put(FieldConstants.FIELD_128, new IFA_LLLCHAR(999, "MAC 2"));


        fieldMap.put(FieldConstants.FIELD_133, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_134, new IFA_LLLCHAR(999, "MAC 2"));

        fieldMap.put(FieldConstants.FIELD_136, new IFA_LLLCHAR(999, "MAC 2"));


        fieldMap.put(FieldConstants.FIELD_140, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_141, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_142, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_143, new IFA_LLLCHAR(999, "MAC 2"));

        fieldMap.put(FieldConstants.FIELD_145, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_146, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_147, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_148, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_149, new IFA_LLLCHAR(999, "MAC 2"));

        fieldMap.put(FieldConstants.FIELD_152, new IFA_LLLCHAR(999, "MAC 2"));

        fieldMap.put(FieldConstants.FIELD_154, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_155, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_156, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_157, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_158, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_159, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_160, new IFA_LLLCHAR(999, "MAC 2"));

        fieldMap.put(FieldConstants.FIELD_162, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_163, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_164, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_165, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_166, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_167, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_168, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_169, new IFA_LLLCHAR(999, "MAC 2"));

        fieldMap.put(FieldConstants.FIELD_171, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_173, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_174, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_175, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_176, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_177, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_178, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_179, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_180, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_181, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_182, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_183, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_184, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_185, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_186, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_187, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_188, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_189, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_190, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_191, new IFA_LLLCHAR(999, "MAC 2"));
        fieldMap.put(FieldConstants.FIELD_192, new IFA_LLLCHAR(999, "MAC 2"));


        for (int i = 0; i <= fields.length; i++) {
            if (fieldMap.containsKey(i)) {
                fields[i] = fieldMap.get(i);
            }
        }
    }

    public TradeRoutePackager() {
        super();
        setFieldPackager(fields);
        try {
            setThirdBitmapField(FieldConstants.FIELD_127);
        } catch (ISOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class TradeRoute172Packager extends SimpleBasePackager {
        private final static Map<Integer, ISOFieldPackager> fieldMap172 = new HashMap<>();
        private final static ISOFieldPackager fld172[] = new ISOFieldPackager[37];

        protected boolean emitBitMap() {
            return (fld[0] instanceof ISOBitMapPackager);
        }

        @Override
        protected int getFirstField() {
            return 0;
        }

        protected ISOFieldPackager getBitMapfieldPackager() {
            return fld[0];
        }

        @Override
        public int unpack(ISOComponent m, byte[] b) throws ISOException {
            return super.unpack(m, b);
        }


        static {
            fieldMap172.put(FieldConstants.FIELD_0, new IFB_BITMAP(8, "Replacement Elements Extended Bit Map"));
            fieldMap172.put(FieldConstants.FIELD_1, new IF_CHAR(15, "Replacement Card Acceptor Id Code"));
            fieldMap172.put(FieldConstants.FIELD_2, new IF_CHAR(8, "Replacement Card Acceptor Terminal Id"));

            for (int i = 0; i <= fld172.length; i++) {
                if (fieldMap172.containsKey(i)) {
                    fld172[i] = fieldMap172.get(i);
                }
            }
        }
    }

    private static class TradeRoute153Packager extends SimpleBasePackager {
        private final static Map<Integer, ISOFieldPackager> fieldMap153 = new HashMap<>();
        private final static ISOFieldPackager fld153[] = new ISOFieldPackager[37];

        protected boolean emitBitMap() {
            return (fld[0] instanceof ISOBitMapPackager);
        }

        @Override
        protected int getFirstField() {
            return 0;
        }

        protected ISOFieldPackager getBitMapfieldPackager() {
            return fld[0];
        }

        @Override
        public int unpack(ISOComponent m, byte[] b) throws ISOException {
            return super.unpack(m, b);
        }


        static {
            fieldMap153.put(FieldConstants.FIELD_0, new IFB_BITMAP(8, "Additional Data Bit Map"));
            fieldMap153.put(FieldConstants.FIELD_1, new IFA_LLLLLBINARY(99999, "Loyalty Invoices"));
            fieldMap153.put(FieldConstants.FIELD_2, new IFA_LLBINARY(99, "Loyalty Card Data"));
            fieldMap153.put(FieldConstants.FIELD_10, new IFA_LLLBINARY(999, "Operator Data"));
            fieldMap153.put(FieldConstants.FIELD_11, new IFA_LLLBINARY(999, "Clearing Data"));
            fieldMap153.put(FieldConstants.FIELD_12, new IF_LENGTH_BINARY(2, "Payment Tokens"));// Has short length 00034FF711 =>  0003 4FF711
            fieldMap153.put(FieldConstants.FIELD_13, new IFA_LLLLBINARY(9999, "Utility Payment Tokens"));
            fieldMap153.put(FieldConstants.FIELD_14, new IFA_LLLLBINARY(9999, "Service Station Data"));
            fieldMap153.put(FieldConstants.FIELD_15, new IFA_LLLBINARY(999, "Private Label Data"));
            fieldMap153.put(FieldConstants.FIELD_16, new IFA_LLCHAR(50, "Card Token"));
            fieldMap153.put(FieldConstants.FIELD_17, new IF_CHAR(12, "Agent Service Fee"));
            fieldMap153.put(FieldConstants.FIELD_18, new IFA_LLLBINARY(999, "Notification Information"));
            fieldMap153.put(FieldConstants.FIELD_19, new IF_LENGTH_BINARY(2, "Messages"));// Has short length 00034FF711 =>  0003 4FF711
            fieldMap153.put(FieldConstants.FIELD_20, new IFA_LLLBINARY(999, "Death Certificate Data"));
            fieldMap153.put(FieldConstants.FIELD_63, new IFA_LLLBINARY(999, "Miscellaneous"));

            for (int i = 0; i <= fld153.length; i++) {
                if (fieldMap153.containsKey(i)) {
                    fld153[i] = fieldMap153.get(i);
                }
            }
        }

        TradeRoute153Packager() {
            super();
            setFieldPackager(fld153);
        }
    }

    private static class TradeRoute150Packager extends SimpleBasePackager {
        private final static Map<Integer, ISOFieldPackager> fieldMap150 = new HashMap<>();
        private final static ISOFieldPackager fld150[] = new ISOFieldPackager[37];

        protected boolean emitBitMap() {
            return (fld[0] instanceof ISOBitMapPackager);
        }

        @Override
        protected int getFirstField() {
            return 0;
        }

        protected ISOFieldPackager getBitMapfieldPackager() {
            return fld[0];
        }

        @Override
        public int unpack(ISOComponent m, byte[] b) throws ISOException {
            return super.unpack(m, b);
        }


        static {
            fieldMap150.put(FieldConstants.FIELD_0, new IFB_BITMAP(5, "Emv Bit Map"));
            fieldMap150.put(FieldConstants.FIELD_1, new IFA_NUMERIC(12, "Amount Authorized"));
            fieldMap150.put(FieldConstants.FIELD_2, new IFA_NUMERIC(12, "Amount Other"));
            fieldMap150.put(FieldConstants.FIELD_3, new IFA_LLBINARY(16, "Application Identifier"));
            fieldMap150.put(FieldConstants.FIELD_4, new IFB_BINARY(2, "Application Interchange Profile"));
            fieldMap150.put(FieldConstants.FIELD_5, new IFB_BINARY(2, "Application Transaction Counter"));
            fieldMap150.put(FieldConstants.FIELD_6, new IFB_BINARY(2, "Application Usage Control"));
            fieldMap150.put(FieldConstants.FIELD_7, new IF_CHAR(2, "Authorization Response Code"));
            fieldMap150.put(FieldConstants.FIELD_8, new IFA_NUMERIC(1, "Card Authentication Reliability Indicator"));
            fieldMap150.put(FieldConstants.FIELD_9, new IF_CHAR(1, "Card Authentication Results Code"));
            fieldMap150.put(FieldConstants.FIELD_10, new IFA_NUMERIC(1, "Chip Condition Code"));
            fieldMap150.put(FieldConstants.FIELD_11, new IFB_BINARY(8, "Cryptogram"));
            fieldMap150.put(FieldConstants.FIELD_12, new IFB_BINARY(1, "Cryptogram Information Data"));
            fieldMap150.put(FieldConstants.FIELD_13, new IFA_LLLBINARY(252, "CVM List"));
            fieldMap150.put(FieldConstants.FIELD_14, new IFB_BINARY(3, "CVM Results"));
            fieldMap150.put(FieldConstants.FIELD_15, new IF_CHAR(8, "Interface Device Serial Number"));
            fieldMap150.put(FieldConstants.FIELD_16, new IFB_BINARY(6, "Issuer Action Code"));
            fieldMap150.put(FieldConstants.FIELD_17, new IFA_LLBINARY(32, "Issuer Application Data"));
            fieldMap150.put(FieldConstants.FIELD_18, new IFA_LLLLBINARY(507, "Issuer Script Results"));
            fieldMap150.put(FieldConstants.FIELD_19, new IF_CHAR(4, "Terminal Application Version Number"));
            fieldMap150.put(FieldConstants.FIELD_20, new IFB_BINARY(3, "Terminal Capabilities"));
            fieldMap150.put(FieldConstants.FIELD_21, new IFA_NUMERIC(3, "Terminal Country Codes"));
            fieldMap150.put(FieldConstants.FIELD_22, new IFA_NUMERIC(2, "Terminal Type"));
            fieldMap150.put(FieldConstants.FIELD_23, new IFB_BINARY(5, "Terminal Verification Result"));
            fieldMap150.put(FieldConstants.FIELD_24, new IF_CHAR(1, "Transaction Category Code"));
            fieldMap150.put(FieldConstants.FIELD_25, new IFA_NUMERIC(3, "Transaction Currency Code"));
            fieldMap150.put(FieldConstants.FIELD_26, new IFA_NUMERIC(6, "Transaction Date"));
            fieldMap150.put(FieldConstants.FIELD_27, new IFA_LCHAR(8, "Transaction Sequence Counter"));
            fieldMap150.put(FieldConstants.FIELD_28, new IFA_NUMERIC(2, "Transaction Type"));
            fieldMap150.put(FieldConstants.FIELD_29, new IFB_BINARY(4, "Unpredictable Number"));
            fieldMap150.put(FieldConstants.FIELD_30, new IFB_LLCHAR(32, "Issuer Authentication Data"));
            fieldMap150.put(FieldConstants.FIELD_31, new IFA_LLLLBINARY(1677, "Issuer Script Template 1"));
            fieldMap150.put(FieldConstants.FIELD_32, new IFA_LLLLBINARY(1677, "Issuer Script Template 2"));
            fieldMap150.put(FieldConstants.FIELD_33, new IFB_BINARY(2, "Transaction Status Information"));
            fieldMap150.put(FieldConstants.FIELD_34, new IFA_LLCHAR(16, "Application Label"));
            fieldMap150.put(FieldConstants.FIELD_35, new IFA_NUMERIC(1, "Auth Adv"));
            fieldMap150.put(FieldConstants.FIELD_36, new IFA_NUMERIC(2, "Contactless Device Profile"));

            for (int i = 0; i <= fld150.length; i++) {
                if (fieldMap150.containsKey(i)) {
                    fld150[i] = fieldMap150.get(i);
                }
            }
        }

        TradeRoute150Packager() {
            super();
            setFieldPackager(fld150);
        }
    }

    private static class TradeRoute151Packager extends SimpleBasePackager {
        private final static Map<Integer, ISOFieldPackager> fieldMap151 = new HashMap<>();
        private final static ISOFieldPackager fld151[] = new ISOFieldPackager[9];

        protected boolean emitBitMap() {
            return (fld[0] instanceof ISOBitMapPackager);
        }

        @Override
        protected int getFirstField() {
            return 0;
        }

        protected ISOFieldPackager getBitMapfieldPackager() {
            return fld[0];
        }


        static {
//            fieldMap151.put(FieldConstants.FIELD_0, new IF_CHAR(0, "PLACEHOLDER"));
            fieldMap151.put(FieldConstants.FIELD_0, new IFB_BITMAP(1, "3D-Secure Bit Map"));
            fieldMap151.put(FieldConstants.FIELD_5, new IFA_NUMERIC(2, "E-Commerce Indicator (ECI Flag)"));
//            fieldMap151.put(FieldConstants.FIELD_2, new IF_CHAR(1, "PIN Entry Device"));
//            fieldMap151.put(FieldConstants.FIELD_3, new IF_CHAR(1, "Terminal Operator"));
//            fieldMap151.put(FieldConstants.FIELD_4, new IF_CHAR(2, "PIN Capture Capability"));
//            fieldMap151.put(FieldConstants.FIELD_5, new IF_CHAR(1, "Card Capture"));
//            fieldMap151.put(FieldConstants.FIELD_6, new IF_CHAR(1, "Terminal Cardholder Authentication Capability"));
//            fieldMap151.put(FieldConstants.FIELD_7, new IF_CHAR(1, "Terminal Output Capability"));

            for (int i = 0; i <= fld151.length; i++) {
                if (fieldMap151.containsKey(i)) {
                    fld151[i] = fieldMap151.get(i);
                }
            }
        }

        TradeRoute151Packager() {
            super();
            setFieldPackager(fld151);
        }
    }

    private static class TradeRoute170Packager extends SimpleBasePackager {

        private final static Map<Integer, ISOFieldPackager> fieldMap170 = new HashMap<>();
        private final static ISOFieldPackager fld170[] = new ISOFieldPackager[4];

        static {
            fieldMap170.put(FieldConstants.FIELD_0, new IFB_LLLHBINARY(0, "Module Name Data Array"));
            fieldMap170.put(FieldConstants.FIELD_1, new IFB_LLLHBINARY(0, "Module Stamp Data Array"));
            fieldMap170.put(FieldConstants.FIELD_2, new IF_CHAR(2, "Module Stamp Data Current Index"));
            fieldMap170.put(FieldConstants.FIELD_3, new IF_LENGTH_BINARY(4,"Message Leg Transit TimeStamp"));// Has integer length 000000034FF711 => 00000003 4FF711

            for (int i = 0; i <= fld170.length; i++) {
                if (fieldMap170.containsKey(i)) {
                    fld170[i] = fieldMap170.get(i);
                }
            }
        }

        TradeRoute170Packager() {
            super();
            setFieldPackager(fld170);
        }

    }

    private static class TradeRoute130Packager extends SimpleBasePackager {
        private final static Map<Integer, ISOFieldPackager> fieldMap130 = new HashMap<>();
        private final static ISOFieldPackager fld130[] = new ISOFieldPackager[8];

        static {
            fieldMap130.put(FieldConstants.FIELD_0, new IF_CHAR(2, "Terminal Type"));
            fieldMap130.put(FieldConstants.FIELD_1, new IF_CHAR(1, "Terminal Location"));
            fieldMap130.put(FieldConstants.FIELD_2, new IF_CHAR(1, "PIN Entry Device"));
            fieldMap130.put(FieldConstants.FIELD_3, new IF_CHAR(1, "Terminal Operator"));
            fieldMap130.put(FieldConstants.FIELD_4, new IF_CHAR(2, "PIN Capture Capability"));
            fieldMap130.put(FieldConstants.FIELD_5, new IF_CHAR(1, "Card Capture"));
            fieldMap130.put(FieldConstants.FIELD_6, new IF_CHAR(1, "Terminal Cardholder Authentication Capability"));
            fieldMap130.put(FieldConstants.FIELD_7, new IF_CHAR(1, "Terminal Output Capability"));

            for (int i = 0; i <= fld130.length; i++) {
                if (fieldMap130.containsKey(i)) {
                    fld130[i] = fieldMap130.get(i);
                }
            }
        }

        TradeRoute130Packager() {
            super();
            setFieldPackager(fld130);
        }
    }

    static class TradeRoute131Packager extends SimpleBasePackager {
        private final static Map<Integer, ISOFieldPackager> fieldMap131 = new HashMap<>();
        private final static ISOFieldPackager fld131[] = new ISOFieldPackager[4];

        static {
            fieldMap131.put(FieldConstants.FIELD_0, new IF_CHAR(1, "Card Present"));
            fieldMap131.put(FieldConstants.FIELD_1, new IF_CHAR(1, "Card Data Capture Capability"));
            fieldMap131.put(FieldConstants.FIELD_2, new IF_CHAR(1, "Card Data Capture Method"));
            fieldMap131.put(FieldConstants.FIELD_3, new IF_CHAR(1, "Card Data Output Capability"));

            for (int i = 0; i <= fld131.length; i++) {
                if (fieldMap131.containsKey(i)) {
                    fld131[i] = fieldMap131.get(i);
                }
            }
        }

        TradeRoute131Packager() {
            super();
            setFieldPackager(fld131);
        }
    }

    static class TradeRoute132Packager extends SimpleBasePackager {
        private final static Map<Integer, ISOFieldPackager> fieldMap = new HashMap<>();
        private final static ISOFieldPackager fld132[] = new ISOFieldPackager[3];

        static {
            fieldMap.put(FieldConstants.FIELD_0, new IF_CHAR(1, "Cardholder Present"));
            fieldMap.put(FieldConstants.FIELD_1, new IF_CHAR(1, "Cardholder Authentication Method"));
            fieldMap.put(FieldConstants.FIELD_2, new IF_CHAR(1, "Cardholder Authentication Entity"));

            for (int i = 0; i <= fld132.length; i++) {
                if (fieldMap.containsKey(i)) {
                    fld132[i] = fieldMap.get(i);
                }
            }
        }

        TradeRoute132Packager() {
            super();
            setFieldPackager(fld132);
        }
    }
}

