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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: org.power.commons.web.CookieUtils <br>
 * CookieUtils
 *
 * @author Kuo Hong
 * @version 2015-09-03
 */
public class CookieUtils {
    /**
     * https://code.google.com/p/util-java/source/browse/trunk/src/utils/
     * CookieUtils.java?r=6
     *
     * @param response
     * @param name
     * @param value
     * @param domain
     * @param maxAge
     */
    public static void setCookie(HttpServletResponse response, String name,
                                 String value, String domain, int maxAge) {
        if (value == null) {
            value = "";
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        if (domain != null && !"".equals(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String getCookieValue(HttpServletRequest request,
                                        String cookieName) {
        if (cookieName == null || request == null) {
            return null;
        }
        Cookie[] cks = request.getCookies();
        if (cks == null) {
            return null;
        }
        for (Cookie cookie : cks) {
            if (cookieName.equals(cookie.getName()))
                return cookie.getValue();
        }
        return null;
    }

    /**
     * @param request
     * @param cookieName
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        if (cookieName == null || request == null) {
            return null;
        }
        Cookie[] cks = request.getCookies();
        if (cks == null) {
            return null;
        }
        for (Cookie cookie : cks) {
            if (cookieName.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }
}
