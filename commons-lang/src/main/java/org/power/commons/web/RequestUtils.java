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

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: org.power.commons.web.RequestUtils <br>
 * RequestUtils
 *
 * @author Kuo Hong
 * @version 2015-09-03
 */
public class RequestUtils {
    public static String getIp(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (!validateIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (!validateIp(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (!validateIp(ip)) {
                    ip = request.getRemoteAddr();
                }
            }
        }
        return ip;
    }

    private static boolean validateIp(String ip) {
        return (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ? false : true;
    }

    /**
     * 获取连接WEB服务器的真实IP，前端有UTR的拦截请求
     */
    public static String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");

        } else {
            String[] ipArray = ip.split("\\,");
            ip = ipArray[0];//  获取第一个IP
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("clientip");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
