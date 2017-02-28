/*
 *     Copyright (C) 2017 Wayne
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


class PostBridge127_25Packager extends SimpleBasePackager {

    private final static Map<Integer, ISOFieldPackager> fieldMap127_25 = new HashMap<>();
    private final static ISOFieldPackager fld127_25[] = new ISOFieldPackager[30 + 1];

    static {
    	fieldMap127_25.put(FieldConstants.FIELD_0, new IF_CHAR(0, "PLACEHOLDER"));
    	fieldMap127_25.put(FieldConstants.FIELD_1, new IFA_BITMAP(8, "BITMAP"));
    	fieldMap127_25.put(FieldConstants.FIELD_2, new IFA_NUMERIC(12, "AMOUNT AUTHORISED"));
    	fieldMap127_25.put(FieldConstants.FIELD_3, new IFA_NUMERIC(12, "AMOUNT OTHER"));
    	fieldMap127_25.put(FieldConstants.FIELD_4, new IFA_LLCHAR(32, "APPLICATION IDENTIFIER"));
    	fieldMap127_25.put(FieldConstants.FIELD_5, new IF_CHAR(4, "APPLICATION INTERCHANGE PROFILE"));    	
    	fieldMap127_25.put(FieldConstants.FIELD_6, new IF_CHAR(4, "APPLICATION TRANSACTION COUNTER"));    	
    	fieldMap127_25.put(FieldConstants.FIELD_7, new IF_CHAR(4, "APPLICATON USAGE CONTROL"));
    	fieldMap127_25.put(FieldConstants.FIELD_8, new IF_CHAR(2, "RETENTION DATA"));
    	fieldMap127_25.put(FieldConstants.FIELD_9, new IFA_NUMERIC(1, "CARD AUTHENTICATION RELIABILITY INDICATOR"));
    	fieldMap127_25.put(FieldConstants.FIELD_10, new IF_CHAR(1, "CARD AUTHENTICATION RESULT CODE"));
    	fieldMap127_25.put(FieldConstants.FIELD_11, new IFA_NUMERIC(1, "CHIP CONDITION CODE"));
    	fieldMap127_25.put(FieldConstants.FIELD_12, new IF_CHAR(16, "TERMINAL OWNDER"));
    	fieldMap127_25.put(FieldConstants.FIELD_13, new IF_CHAR(2, "CRYPTOGRAM INFORMATION DATA"));
    	fieldMap127_25.put(FieldConstants.FIELD_14, new IFA_LLLCHAR(504, "CVM LIST"));
    	fieldMap127_25.put(FieldConstants.FIELD_15, new IF_CHAR(6, "CVM RESULTS"));
    	fieldMap127_25.put(FieldConstants.FIELD_16, new IF_CHAR(8, "INTERFACE DEVICE SERIAL NUMBER"));
    	fieldMap127_25.put(FieldConstants.FIELD_17, new IF_CHAR(11, "ISSUER ACTION CODE"));
    	fieldMap127_25.put(FieldConstants.FIELD_18, new IFA_LLCHAR(64, "ISUER APPLICATION DATA"));
    	fieldMap127_25.put(FieldConstants.FIELD_19, new IFA_LLLLCHAR(507, "ISSUER SCRIPT RESULTS"));
    	fieldMap127_25.put(FieldConstants.FIELD_20, new IF_CHAR(4, "TERMINAL APPLICATION VERSION NUMBER"));
    	fieldMap127_25.put(FieldConstants.FIELD_21, new IF_CHAR(6, "TERMINAL CAPABILITIES"));
    	fieldMap127_25.put(FieldConstants.FIELD_22, new IFA_NUMERIC(3, "TERMINAL COUNTRY CODE"));
    	fieldMap127_25.put(FieldConstants.FIELD_23, new IFA_NUMERIC(2, "TERMINAL TYPE"));
    	fieldMap127_25.put(FieldConstants.FIELD_24, new IF_CHAR(10, "TERMINAL VERIFICATION RESULT"));
    	fieldMap127_25.put(FieldConstants.FIELD_25, new IF_CHAR(1, "TRANSACTION CATEGORY CODE"));
    	fieldMap127_25.put(FieldConstants.FIELD_26, new IFA_NUMERIC(3, "ICC DATA"));
    	fieldMap127_25.put(FieldConstants.FIELD_27, new IFA_NUMERIC(6, "TRANSACTION DATE"));
    	fieldMap127_25.put(FieldConstants.FIELD_28, new IFA_LCHAR(8, "TRANSACTION SEQUENCE COUNTER"));
    	fieldMap127_25.put(FieldConstants.FIELD_29, new IFA_NUMERIC(2, "TRANSACTION TYPE"));
    	fieldMap127_25.put(FieldConstants.FIELD_30, new IF_CHAR(8, "UNPREDICTABLE NUMBER"));

    	for (int i = 0; i <= fld127_25.length; i++) {
            if (fieldMap127_25.containsKey(i)) {
            	fld127_25[i] = fieldMap127_25.get(i);
            }
        }
    }

    public PostBridge127_25Packager() {
        super();
        setFieldPackager(fld127_25);
    }

}
