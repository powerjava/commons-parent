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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * 配置导入工具
 *
 * @author Kuo Hong
 * @version 2014-09-22
 */
public class ConfigLoaderUtils {
    protected static final Logger LOGGER = LoggerFactory
            .getLogger(ConfigLoaderUtils.class);
    public static String CLASS_PATH = "";

    // loader
    private static ClassLoader loader = ConfigLoaderUtils.class
            .getClassLoader();

    //
    // get class path
    //
    static {

        if (loader == null) {
            LOGGER.info("using system class loader!");
            loader = ClassLoader.getSystemClassLoader();
        }
        java.net.URL url = loader.getResource("");

        try {
            // get class path
            CLASS_PATH = url.getPath();
            CLASS_PATH = URLDecoder.decode(CLASS_PATH, "utf-8");
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
    }


    private ConfigLoaderUtils() {

    }

    /**
     * @param propertyFilePath
     * @return void
     * @Description: 使用TOMCAT方式来导入
     * @author liaoqiqi
     * @date 2013-6-19
     */
    private static Properties loadWithTomcatMode(final String propertyFilePath)
            throws Exception {

        Properties props = new Properties();

        try {

            // 先用TOMCAT模式进行导入
            // http://blog.csdn.net/minfree/article/details/1800311
            // http://stackoverflow.com/questions/3263560/sysloader-getresource-problem-in-java
            URL url = loader.getResource(propertyFilePath);
            URI uri = new URI(url.toString());
            props.load(new FileInputStream(uri.getPath()));

        } catch (Exception e) {

            // http://stackoverflow.com/questions/574809/load-a-resource-contained-in-a-jar
            props.load(loader.getResourceAsStream(propertyFilePath));
        }
        return props;
    }

    /**
     * @param propertyFilePath
     * @return void
     * @Description: 使用普通模式导入
     * @author liaoqiqi
     * @date 2013-6-19
     */
    private static Properties loadWithNormalMode(final String propertyFilePath)
            throws Exception {

        Properties props = new Properties();
        props.load(new FileInputStream(propertyFilePath));
        return props;
    }

    /**
     * @param propertyFilePath
     * @return Properties
     * @throws Exception
     * @Description: 配置文件载入器助手
     * @author liaoqiqi
     * @date 2013-6-19
     */
    public static Properties loadConfig(final String propertyFilePath)
            throws Exception {

        try {

            // 用TOMCAT模式 来载入试试
            return ConfigLoaderUtils.loadWithTomcatMode(propertyFilePath);

        } catch (Exception e1) {

            try {
                // 用普通模式进行载入
                return loadWithNormalMode(propertyFilePath);

            } catch (Exception e2) {

                throw new Exception("cannot load config file: "
                        + propertyFilePath);
            }
        }
    }

    /**
     * @param filePath
     * @return InputStream
     * @Description: 采用两种方式来载入文件
     * @author liaoqiqi
     * @date 2013-6-20
     */
    public static InputStream loadFile(String filePath) {

        InputStream in = null;

        try {

            // 先用TOMCAT模式进行导入
            in = loader.getResourceAsStream(filePath);
            if (in == null) {

                // 使用普通模式导入
                try {

                    return new FileInputStream(filePath);

                } catch (FileNotFoundException e) {
                    return null;
                }
            } else {

                return in;
            }

        } finally {

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error("WHY HERE!", e);
                }
            }
        }
    }

}
