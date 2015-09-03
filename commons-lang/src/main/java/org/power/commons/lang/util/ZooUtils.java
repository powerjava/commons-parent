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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: org.power.commons.lang.util <br>
 * //TODO insert into titile here
 *
 * @author Kuo Hong
 * @version 2015-09-03
 */
public class ZooUtils {
    protected static final Logger LOGGER = LoggerFactory
            .getLogger(ZooUtils.class);

    private ZooUtils() {

    }

    /**
     * 一个可读性良好的路径Value
     *
     * @return
     */
    public static String getIp() {

        try {
            return SystemUtils.getHostInfo().getAddress();
        } catch (Exception e) {
            LOGGER.error("cannot get host info", e);
            return "";
        }
    }
}
