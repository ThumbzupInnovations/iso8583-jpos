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

package org.lostcoder.iso8583;

import org.lostcoder.iso8583.exception.Iso8583Exception;

/**
 * Created by Sean on 2016/09/15.
 */
public class JposService implements Iso8583Service {
    @Override
    public Iso8583Message create(String mti, AcquirerProtocol protocol) throws Iso8583Exception {
        return new JposMessage(mti, protocol);
    }

    @Override
    public Iso8583Message create(byte[] isodata, AcquirerProtocol protocol) throws Iso8583Exception {
        return new JposMessage(isodata, protocol);
    }

    @Override
    public boolean supports(AcquirerProtocol protocol) {
        switch (protocol) {
            case BASE24:
                return true;
            case AS2805:
                return true;
        }
        return false;
    }
}
