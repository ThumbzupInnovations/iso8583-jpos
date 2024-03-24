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


class PostBridge127_49Packager extends SimpleBasePackager {

    private final static Map<Integer, ISOFieldPackager> fieldMap127_49 = new HashMap<>();
    private final static ISOFieldPackager fld127_49[] = new ISOFieldPackager[14+1];

    static {
        fieldMap127_49.put(FieldConstants.FIELD_0, new IF_CHAR(0, "PLACEHOLDER"));
        fieldMap127_49.put(FieldConstants.FIELD_2, new IFA_LLLCHAR(16, "Card Acceptor Phone Number"));
        fieldMap127_49.put(FieldConstants.FIELD_3, new IFA_LLLCHAR(48, "Street Address"));
        fieldMap127_49.put(FieldConstants.FIELD_5, new IF_CHAR(3, "Country Subdivision Code"));
        fieldMap127_49.put(FieldConstants.FIELD_6, new IFA_LLLCHAR(255, "URL"));
        fieldMap127_49.put(FieldConstants.FIELD_7, new IFA_LLLCHAR(16, "Customer Service Phone Number"));
        fieldMap127_49.put(FieldConstants.FIELD_11, new IFA_LLLCHAR(13, "Service Location City Name"));
        fieldMap127_49.put(FieldConstants.FIELD_12, new IF_CHAR(3, "Service Location Country Subdivision Code"));
        fieldMap127_49.put(FieldConstants.FIELD_13, new IF_CHAR(3, "Service Location Country Code"));
        fieldMap127_49.put(FieldConstants.FIELD_14, new IFA_LLLCHAR(10, "Service Location Postal Code"));


        for (int i = 0; i <= fld127_49.length; i++) {
            if (fieldMap127_49.containsKey(i)) {
                fld127_49[i] = fieldMap127_49.get(i);
            }
        }
    }

    public PostBridge127_49Packager() {
        super();
        setFieldPackager(fld127_49);
    }

}
