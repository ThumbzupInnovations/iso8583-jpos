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


class PostBridge127Packager extends SimpleBasePackager {

    private final static Map<Integer, ISOFieldPackager> fieldMap127 = new HashMap<>();
    private final static ISOFieldPackager fld127[] = new ISOFieldPackager[25 + 1];

    static {
        fieldMap127.put(FieldConstants.FIELD_0, new IF_CHAR(0, "PLACEHOLDER"));
        fieldMap127.put(FieldConstants.FIELD_1, new IFB_BITMAP(8, "BITMAP"));
        fieldMap127.put(FieldConstants.FIELD_2, new IFA_LLCHAR(32, "SWITCH KEY"));
        fieldMap127.put(FieldConstants.FIELD_3, new IF_CHAR(48, "ROUTING INFORMATION"));
        fieldMap127.put(FieldConstants.FIELD_4, new IF_CHAR(22, "POS DATA"));
        fieldMap127.put(FieldConstants.FIELD_5, new IF_CHAR(73, "SERVICE STATION DATA"));
        fieldMap127.put(FieldConstants.FIELD_6, new IFA_NUMERIC(2, "AUTHORIZATION PROFILE"));
        fieldMap127.put(FieldConstants.FIELD_7, new IFA_LLCHAR(50, "CHECK DATA"));
        fieldMap127.put(FieldConstants.FIELD_8, new IFA_LLLCHAR(128, "RETENTION DATA"));
        fieldMap127.put(FieldConstants.FIELD_9, new IFA_LLLCHAR(255, "ADDITIONAL NODE DATA"));
        fieldMap127.put(FieldConstants.FIELD_10, new IFA_NUMERIC(3, "CVV2"));
        fieldMap127.put(FieldConstants.FIELD_11, new IFA_LLCHAR(32, "ORIGINAL KEY"));
        fieldMap127.put(FieldConstants.FIELD_12, new IFA_LLCHAR(25, "TERMINAL OWNDER"));
        fieldMap127.put(FieldConstants.FIELD_13, new IF_CHAR(17, "POS GEOGRAPHIC DATA"));
        fieldMap127.put(FieldConstants.FIELD_14, new IF_CHAR(8, "SPONSOR BANK"));
        fieldMap127.put(FieldConstants.FIELD_15, new IFA_LLCHAR(29, "AVS REQUEST"));
        fieldMap127.put(FieldConstants.FIELD_16, new IF_CHAR(1, "AVS RESPONSE"));
        fieldMap127.put(FieldConstants.FIELD_17, new IFA_LLCHAR(50, "CARDHOLDER INFORMATION"));
        fieldMap127.put(FieldConstants.FIELD_18, new IFA_LLCHAR(50, "VALIDATION DATA"));
        fieldMap127.put(FieldConstants.FIELD_19, new IF_CHAR(45, "BANK DETAILS"));
        fieldMap127.put(FieldConstants.FIELD_20, new IFA_NUMERIC(8, "AUTHORIZER DATE SETTLEMENT"));
        fieldMap127.put(FieldConstants.FIELD_21, new IFA_LLCHAR(12, "RECORD IDENTIFICATION"));
        fieldMap127.put(FieldConstants.FIELD_22, new IFA_LLLLLCHAR(99999, "STRUCTURED DATA"));
        fieldMap127.put(FieldConstants.FIELD_23, new IF_CHAR(253, "PAYEE NAME AND ADDRESS"));
        fieldMap127.put(FieldConstants.FIELD_24, new IFA_LLCHAR(28, "PAYER ACCOUNT INFORMATION"));
        fieldMap127.put(FieldConstants.FIELD_25, new IFA_LLLLCHAR(8000, "ICC DATA"));
        for (int i = 0; i <= fld127.length; i++) {
            if (fieldMap127.containsKey(i)) {
                fld127[i] = fieldMap127.get(i);
            }
        }
    }

    public PostBridge127Packager() {
        super();
        setFieldPackager(fld127);
    }

}
