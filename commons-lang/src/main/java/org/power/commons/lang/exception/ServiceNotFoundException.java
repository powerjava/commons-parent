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

package org.power.commons.lang.exception;

/**
 * 代表<code>META-INF/services/</code>中的文件未找到或读文件失败的异常�?
 *
 * @author Geiger
 */
public class ServiceNotFoundException extends ClassNotFoundException {
    private static final long serialVersionUID = -2993107602317534281L;

    /**
     * Constructs a <code>ClassNotFoundException</code> with the
     * specified detail message.
     *
     * @param s the detail message.
     */
    public ServiceNotFoundException(String s) {
        super(s);
    }

    /**
     * Constructs a <code>ClassNotFoundException</code> with the
     * specified detail message and optional exception that was
     * raised while loading the class.
     *
     * @param s  the detail message
     * @param ex the exception that was raised while loading the class
     * @since 1.2
     */
    public ServiceNotFoundException(String s, Throwable ex) {
        super(s, ex);
    }
}
