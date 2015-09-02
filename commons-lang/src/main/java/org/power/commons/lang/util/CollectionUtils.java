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


import org.apache.commons.lang3.StringUtils;
import org.power.commons.lang.BasicConstant;

import java.io.IOException;
import java.util.*;

/**
 * @author Geiger
 */
public class CollectionUtils {
    /**
     * 创建一个<code>List</code>。
     * <p>
     * 和{@code createArrayList(args)}不同，本方法会返回一个不可变长度的列表，且性能高于
     * {@code createArrayList(args)}。
     * </p>
     */
    public static <T> List<T> asList(T... args) {
        if (args == null || args.length == 0) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(args);
        }
    }

    /**
     * 将列表中的对象连接成字符串。
     */
    public static String join(Iterable<?> objs, String sep) {
        StringBuilder buf = new StringBuilder();

        join(buf, objs, sep);

        return buf.toString();
    }

    /**
     * 将列表中的对象连接起来。
     */
    public static void join(StringBuilder buf, Iterable<?> objs, String sep) {
        try {
            join((Appendable) buf, objs, sep);
        } catch (IOException e) {
            Assert.unexpectedException(e);
        }
    }

    /**
     * 将列表中的对象连接起来。
     */
    public static void join(Appendable buf, Iterable<?> objs, String sep) throws IOException {
        if (objs == null) {
            return;
        }

        if (sep == null) {
            sep = BasicConstant.EMPTY_STRING;
        }

        for (Iterator<?> i = objs.iterator(); i.hasNext(); ) {
            buf.append(String.valueOf(i.next()));

            if (i.hasNext()) {
                buf.append(sep);
            }
        }
    }


    /**
     * 转换Collection所有元素(通过toString())为String, 中间以 separator分隔。
     */
    public static String convertToString(final Collection collection, final String separator) {
        return StringUtils.join(collection, separator);
    }

    /**
     * 转换Collection所有元素(通过toString())为String, 每个元素的前面加入prefix，后面加入postfix，如<div>mymessage</div>。
     */
    public static String convertToString(final Collection collection, final String prefix, final String postfix) {
        StringBuilder builder = new StringBuilder();
        for (Object o : collection) {
            builder.append(prefix).append(o).append(postfix);
        }
        return builder.toString();
    }


    private static <T> void iterableToCollection(Iterable<? extends T> c, Collection<T> list) {
        for (T element : c) {
            list.add(element);
        }
    }

    public static boolean isEmpty(Collection collection) {
        if (collection != null) {
            return collection.isEmpty();
        } else {
            return true;
        }
    }

    public static boolean isEmpty(Map map) {
        if (map != null) {
            return map.isEmpty();
        } else {
            return true;
        }
    }


}
