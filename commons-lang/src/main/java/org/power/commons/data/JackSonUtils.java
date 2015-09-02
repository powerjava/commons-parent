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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import org.power.commons.lang.util.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * //TODO insert into titile here
 *
 * @author Kuo Hong
 * @version 2015-09-02
 */
public class JackSonUtils {
    final static ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(JackSonUtils.class);

    static {
        //StdSerializerProvider sp = new StdSerializerProvider();
        // sp.setNullValueSerializer(new NullNullSerializer());
        SerializerProvider sp = new DefaultSerializerProvider.Impl();
        sp.setNullValueSerializer(NullSerializer.instance);
        objectMapper = new ObjectMapper();
        // objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // objectMapper.setDateFormat(new
        // SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    private JackSonUtils() {

    }

    ;

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * JSON串转换为Java泛型对象，可以是各种类型，此方法最为强大。用法看测试用例。
     *
     * @param <T>
     * @param jsonString JSON字符串
     * @param tr         TypeReference,例如: new TypeReference< List<FamousUser> >(){}
     * @return List对象列表
     */
    @SuppressWarnings("unchecked")
    public static <T> T json2GenericObject(String jsonString,
                                           TypeReference<T> tr) {

        if (jsonString == null || "".equals(jsonString)) {
            return null;
        } else {
            try {
                return (T) objectMapper.readValue(jsonString, tr);
            } catch (Exception e) {
                log.warn("json error:" + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Java对象转Json字符串
     */
    public static String toJson(Object object) {
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.warn("json error:" + e.getMessage());
        }
        return jsonString;

    }

    /**
     * Json字符串转Java对象
     */
    public static Object json2Object(String jsonString, Class<?> c) {

        if (jsonString == null || "".equals(jsonString)) {
            return "";
        } else {
            try {
                return objectMapper.readValue(jsonString, c);
            } catch (Exception e) {
                log.warn("json error:" + e.getMessage());
            }

        }
        return "";
    }

    public static final class IosDateTimeJsonSerializer extends
            JsonSerializer<Date> {

        @Override
        public void serialize(Date value, JsonGenerator paramJsonGenerator,
                              SerializerProvider provider) throws IOException,
                JsonProcessingException {
            if (value != null) {
                paramJsonGenerator.writeString(DateUtils.ISO_DATETIME_FORMAT
                        .format(value));
            }
        }

    }
}
