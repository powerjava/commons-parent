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

package org.power.commons.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * ClassName: org.power.commons.web.NetUtils <br>
 * NetUtils
 *
 * @author Kuo Hong
 * @version 2015-09-03
 */
public class NetUtils {
    protected static final Logger LOGGER = LoggerFactory
            .getLogger(NetUtils.class);

    private NetUtils() {

    }

    /**
     * PING指定URL是否可用
     *
     * @param address
     * @return
     */
    public static boolean pingUrl(final String address) {

        try {

            final URL url = new URL("http://" + address);

            final HttpURLConnection urlConn = (HttpURLConnection) url
                    .openConnection();

            urlConn.setConnectTimeout(1000 * 10); // mTimeout is in seconds

            final long startTime = System.currentTimeMillis();

            urlConn.connect();

            final long endTime = System.currentTimeMillis();

            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                LOGGER.info("Time (ms) : " + (endTime - startTime));
                LOGGER.info("Ping to " + address + " was success");
                return true;
            }

        } catch (final MalformedURLException e1) {
            e1.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
