/*
 * 	Copyright (c) 2015 Power Group.
 * 	 Licensed under the Apache License, Version 2.0 (the "License");
 * 	you may not use this file except in compliance with the License.
 * 	You may obtain a copy of the License at
 *
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * 	Unless required by applicable law or agreed to in writing, software
 * 	distributed under the License is distributed on an "AS IS" BASIS,
 * 	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 	See the License for the specific language governing permissions and
 * 	limitations under the License.
 *
 */

package org.power.commons.lang.util;

import java.util.Date;

/**
 * ClassName: org.power.commons.lang.util.TokenUtils <br>
 *
 * @author Kuo Hong
 * @version 2015-09-03
 */
public class TokenUtils {
    private TokenUtils() {

    }

    public static final String getTokenId(int length) {
        if (0 == length) {
            return "";
        }

        Date tt = new Date();
        String mTimeStr = "" + tt.getTime();
        int realLength = mTimeStr.length();
        if (realLength >= length) {
            return mTimeStr.substring(0, length);
        }

        String tempString = "";
        String randomString = mTimeStr;

        while (realLength < length) {
            tempString = "" + Math.random();
            randomString += tempString.substring(2);
            realLength = randomString.length();
        }

        return randomString.substring(0, length);
    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static final String generateToken() {
        return java.util.UUID.randomUUID().toString();
    }
}
