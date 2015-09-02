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

package org.power.commons.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Google Json Utils
 *
 * @author Kuo Hong
 * @version 2015-09-02
 */
public class GsonUtils {
    private GsonUtils() {

    }

    /**
     * @param object
     * @return
     */
    public static String toJson(Object object) {

        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    /**
     * Parse json to map
     *
     * @param json
     * @return
     */
    public static Map<String, String> parse2Map(String json) {

        Type stringStringMap = new TypeToken<Map<String, String>>() {
        }.getType();

        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(json, stringStringMap);

        return map;
    }
}
