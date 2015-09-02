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

package org.power.commons.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.WeakHashMap;

/**
 * 读取properties文件的辅助类
 *
 * @author Kuo Hong
 * @version 2015-09-02
 */
public class PropertiesReader {
    private static Logger logger = LoggerFactory.getLogger(PropertiesReader.class);
    private static Map<String, Properties> filePropMapping = new WeakHashMap<String, Properties>();

    private PropertiesReader() {

    }

    /**
     * 取得指定properties文件的指定key的value
     *
     * @param fileName
     * @param key
     * @return
     * @throws MissingResourceException
     */
    public static String getValue(String fileName, String key)
            throws MissingResourceException {
        final Properties properties = fillProperties(fileName);
        String value = properties.getProperty(key);
        return value.trim();
    }

    /**
     * 将文件中配置信息填充到properties对象中(用earth的ClassLoader)
     *
     * @param fileName
     * @return Properties对象
     * @author liuzeyin
     */
    public static Properties fillProperties(String fileName) {
        return fillProperties(fileName, PropertiesReader.class.getClassLoader());
    }

    /**
     * 将文件中配置信息填充到properties对象中(用指定的ClassLoader)
     *
     * @param fileName
     * @param cl
     * @return Properties对象
     * @author liuzeyin
     */
    public static Properties fillProperties(String fileName, ClassLoader cl) {

        if (!fileName.endsWith(".properties")) {
            fileName = fileName + ".properties";
        }

        Properties properties = new Properties();

        if (filePropMapping.containsKey(fileName)) {
            properties = filePropMapping.get(fileName);
        } else {
            InputStream is = cl.getResourceAsStream(fileName);
            try {
                properties.load(is);
                filePropMapping.put(fileName, properties);
            } catch (Exception e) {
                throw new RuntimeException("load properties file error "
                        + fileName, e);
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("close InputStream error ", e);
                }
            }
        }

        return properties;

    }
}
