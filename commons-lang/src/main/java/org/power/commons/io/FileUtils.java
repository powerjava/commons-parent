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

package org.power.commons.io;

import org.apache.commons.lang3.StringUtils;
import org.power.commons.lang.util.IllegalPathException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用来处理文件路径和后缀的工具。
 *
 * @author Geiger
 */
public class FileUtils extends org.apache.commons.io.FileUtils{
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    // ==========================================================================
    // 规格化路径。
    // ==========================================================================

    private static final Pattern schemePrefixPattern = Pattern.compile(
            "(file:/*[a-z]:)|(\\w+://.+?/)|((jar|zip):.+!/)|(\\w+:)", Pattern.CASE_INSENSITIVE);

    /**
     * 规格化绝对路径。
     * <p>
     * 该方法返回以“<code>/</code>”开始的绝对路径。转换规则如下：
     * </p>
     * <ol>
     * <li>路径为空，则返回<code>""</code>。</li>
     * <li>将所有backslash("\\")转化成slash("/")。</li>
     * <li>去除重复的"/"或"\\"。</li>
     * <li>去除"."，如果发现".."，则向上朔一级目录。</li>
     * <li>保留路径末尾的"/"（如果有的话，除了空路径）。</li>
     * <li>对于绝对路径，如果".."上朔的路径超过了根目录，则看作非法路径，抛出异常。</li>
     * </ol>
     *
     * @param path 要规格化的路径
     * @return 规格化后的路径
     * @throws IllegalPathException 如果路径非法
     */
    public static String normalizeAbsolutePath(String path) throws IllegalPathException {
        return normalizePath(path, true, false, false);
    }

    /**
     * 规格化绝对路径。
     * <p>
     * 该方法返回以“<code>/</code>”开始的绝对路径。转换规则如下：
     * </p>
     * <ol>
     * <li>路径为空，则返回<code>""</code>。</li>
     * <li>将所有backslash("\\")转化成slash("/")。</li>
     * <li>去除重复的"/"或"\\"。</li>
     * <li>去除"."，如果发现".."，则向上朔一级目录。</li>
     * <li>保留路径末尾的"/"（如果有的话，除了空路径和强制指定<code>removeTrailingSlash==true</code>）。</li>
     * <li>对于绝对路径，如果".."上朔的路径超过了根目录，则看作非法路径，抛出异常。</li>
     * </ol>
     *
     * @param path                要规格化的路径
     * @param removeTrailingSlash 是否强制移除末尾的<code>"/"</code>
     * @return 规格化后的路径
     * @throws IllegalPathException 如果路径非法
     */
    public static String normalizeAbsolutePath(String path, boolean removeTrailingSlash) throws IllegalPathException {
        return normalizePath(path, true, false, removeTrailingSlash);
    }

    /**
     * 规格化相对路径。
     * <p>
     * 该方法返回不以“<code>/</code>”开始的相对路径。转换规则如下：
     * </p>
     * <ol>
     * <li>路径为空，则返回<code>""</code>。</li>
     * <li>将所有backslash("\\")转化成slash("/")。</li>
     * <li>去除重复的"/"或"\\"。</li>
     * <li>去除"."，如果发现".."，则向上朔一级目录。</li>
     * <li>空相对路径返回""。</li>
     * <li>保留路径末尾的"/"（如果有的话，除了空路径）。</li>
     * </ol>
     *
     * @param path 要规格化的路径
     * @return 规格化后的路径
     * @throws IllegalPathException 如果路径非法
     */
    public static String normalizeRelativePath(String path) throws IllegalPathException {
        return normalizePath(path, false, true, false);
    }

    /**
     * 规格化相对路径。
     * <p>
     * 该方法返回不以“<code>/</code>”开始的相对路径。转换规则如下：
     * </p>
     * <ol>
     * <li>路径为空，则返回<code>""</code>。</li>
     * <li>将所有backslash("\\")转化成slash("/")。</li>
     * <li>去除重复的"/"或"\\"。</li>
     * <li>去除"."，如果发现".."，则向上朔一级目录。</li>
     * <li>空相对路径返回""。</li>
     * <li>保留路径末尾的"/"（如果有的话，除了空路径和强制指定<code>removeTrailingSlash==true</code>）。</li>
     * </ol>
     *
     * @param path                要规格化的路径
     * @param removeTrailingSlash 是否强制移除末尾的<code>"/"</code>
     * @return 规格化后的路径
     * @throws IllegalPathException 如果路径非法
     */
    public static String normalizeRelativePath(String path, boolean removeTrailingSlash) throws IllegalPathException {
        return normalizePath(path, false, true, removeTrailingSlash);
    }

    /**
     * 规格化路径。规则如下：
     * <ol>
     * <li>路径为空，则返回<code>""</code>。</li>
     * <li>将所有backslash("\\")转化成slash("/")。</li>
     * <li>去除重复的"/"或"\\"。</li>
     * <li>去除"."，如果发现".."，则向上朔一级目录。</li>
     * <li>空绝对路径返回"/"，空相对路径返回""。</li>
     * <li>保留路径末尾的"/"（如果有的话，除了空路径）。</li>
     * <li>对于绝对路径，如果".."上朔的路径超过了根目录，则看作非法路径，抛出异常。</li>
     * </ol>
     *
     * @param path 要规格化的路径
     * @return 规格化后的路径
     * @throws IllegalPathException 如果路径非法
     */
    public static String normalizePath(String path) throws IllegalPathException {
        return normalizePath(path, false, false, false);
    }

    /**
     * 规格化路径。规则如下：
     * <ol>
     * <li>路径为空，则返回<code>""</code>。</li>
     * <li>将所有backslash("\\")转化成slash("/")。</li>
     * <li>去除重复的"/"或"\\"。</li>
     * <li>去除"."，如果发现".."，则向上朔一级目录。</li>
     * <li>空绝对路径返回"/"，空相对路径返回""。</li>
     * <li>保留路径末尾的"/"（如果有的话，除了空路径和强制指定<code>removeTrailingSlash==true</code>）。</li>
     * <li>对于绝对路径，如果".."上朔的路径超过了根目录，则看作非法路径，抛出异常。</li>
     * </ol>
     *
     * @param path                要规格化的路径
     * @param removeTrailingSlash 是否强制移除末尾的<code>"/"</code>
     * @return 规格化后的路径
     * @throws IllegalPathException 如果路径非法
     */
    public static String normalizePath(String path, boolean removeTrailingSlash) throws IllegalPathException {
        return normalizePath(path, false, false, removeTrailingSlash);
    }

    private static String normalizePath(String path, boolean forceAbsolute, boolean forceRelative,
                                        boolean removeTrailingSlash) throws IllegalPathException {
        char[] pathChars = StringUtils.trimToEmpty(path).toCharArray();
        int length = pathChars.length;

        // 检查绝对路径，以及path尾部的"/"
        boolean startsWithSlash = false;
        boolean endsWithSlash = false;

        if (length > 0) {
            char firstChar = pathChars[0];
            char lastChar = pathChars[length - 1];

            startsWithSlash = firstChar == '/' || firstChar == '\\';
            endsWithSlash = lastChar == '/' || lastChar == '\\';
        }

        StringBuilder buf = new StringBuilder(length);
        boolean isAbsolutePath = forceAbsolute || !forceRelative && startsWithSlash;
        int index = startsWithSlash ? 0 : -1;
        int level = 0;

        if (isAbsolutePath) {
            buf.append("/");
        }

        while (index < length) {
            // 跳到第一个非slash字符，或末尾
            index = indexOfSlash(pathChars, index + 1, false);

            if (index == length) {
                break;
            }

            // 取得下一个slash index，或末尾
            int nextSlashIndex = indexOfSlash(pathChars, index, true);

            String element = new String(pathChars, index, nextSlashIndex - index);
            index = nextSlashIndex;

            // 忽略"."
            if (".".equals(element)) {
                continue;
            }

            // 回朔".."
            if ("..".equals(element)) {
                if (level == 0) {
                    // 如果是绝对路径，../试图越过最上层目录，这是不可能的，
                    // 抛出路径非法的异常。
                    if (isAbsolutePath) {
                        throw new IllegalPathException(path);
                    } else {
                        buf.append("../");
                    }
                } else {
                    buf.setLength(pathChars[--level]);
                }

                continue;
            }

            // 添加到path
            pathChars[level++] = (char) buf.length(); // 将已经读过的chars空间用于记录指定level的index
            buf.append(element).append('/');
        }

        // 除去最后的"/"
        if (buf.length() > 0) {
            if (!endsWithSlash || removeTrailingSlash) {
                buf.setLength(buf.length() - 1);
            }
        }

        return buf.toString();
    }

    // ==========================================================================
    // 取得基于指定basedir规格化路径。
    // ==========================================================================

    private static int indexOfSlash(char[] chars, int beginIndex, boolean slash) {
        int i = beginIndex;

        for (; i < chars.length; i++) {
            char ch = chars[i];

            if (slash) {
                if (ch == '/' || ch == '\\') {
                    break; // if a slash
                }
            } else {
                if (ch != '/' && ch != '\\') {
                    break; // if not a slash
                }
            }
        }

        return i;
    }

    /**
     * 如果指定路径已经是绝对路径，则规格化后直接返回之，否则取得基于指定basedir的规格化路径。
     *
     * @param basedir 根目录，如果<code>path</code>为相对路径，表示基于此目录
     * @param path    要检查的路径
     * @return 规格化的绝对路径
     * @throws IllegalPathException 如果路径非法
     */
    public static String getAbsolutePathBasedOn(String basedir, String path) throws IllegalPathException {
        // 如果path为绝对路径，则规格化后返回
        boolean isAbsolutePath = false;

        path = StringUtils.trimToEmpty(path);

        if (path.length() > 0) {
            char firstChar = path.charAt(0);
            isAbsolutePath = firstChar == '/' || firstChar == '\\';
        }

        if (!isAbsolutePath) {
            // 如果path为相对路径，将它和basedir合并。
            if (path.length() > 0) {
                path = StringUtils.trimToEmpty(basedir) + "/" + path;
            } else {
                path = StringUtils.trimToEmpty(basedir);
            }
        }

        return normalizeAbsolutePath(path);
    }

    // ==========================================================================
    // 取得相对于指定basedir相对路径。
    // ==========================================================================

    /**
     * 取得和系统相关的绝对路径。
     *
     * @throws IllegalPathException 如果basedir不是绝对路径
     */
    public static String getSystemDependentAbsolutePathBasedOn(String basedir, String path) {
        path = StringUtils.trimToEmpty(path);

        boolean endsWithSlash = path.endsWith("/") || path.endsWith("\\");

        File pathFile = new File(path);

        if (pathFile.isAbsolute()) {
            // 如果path已经是绝对路径了，则直接返回之。
            path = pathFile.getAbsolutePath();
        } else {
            // 否则以basedir为基本路径。
            // 下面确保basedir本身为绝对路径。
            basedir = StringUtils.trimToEmpty(basedir);

            File baseFile = new File(basedir);

            if (baseFile.isAbsolute()) {
                path = new File(baseFile, path).getAbsolutePath();
            } else {
                throw new IllegalPathException("Basedir is not absolute path: " + basedir);
            }
        }

        if (endsWithSlash) {
            path = path + '/';
        }

        return normalizePath(path);
    }

    // ==========================================================================
    // 取得文件名后缀。
    // ==========================================================================

    /**
     * 取得相对于指定根目录的相对路径。
     *
     * @param basedir 根目录
     * @param path    要计算的路径
     * @return 如果<code>path</code>和<code>basedir</code>是兼容的，则返回相对于
     * <code>basedir</code>的相对路径，否则返回<code>path</code>本身。
     * @throws IllegalPathException 如果路径非法
     */
    public static String getRelativePath(String basedir, String path) throws IllegalPathException {
        // 取得规格化的basedir，确保其为绝对路径
        basedir = normalizeAbsolutePath(basedir);

        // 取得规格化的path
        path = getAbsolutePathBasedOn(basedir, path);

        // 保留path尾部的"/"
        boolean endsWithSlash = path.endsWith("/");

        // 按"/"分隔basedir和path
        String[] baseParts = StringUtils.split(basedir, '/');
        String[] parts = StringUtils.split(path, '/');
        StringBuilder buf = new StringBuilder();
        int i = 0;

        while (i < baseParts.length && i < parts.length && baseParts[i].equals(parts[i])) {
            i++;
        }

        if (i < baseParts.length && i < parts.length) {
            for (int j = i; j < baseParts.length; j++) {
                buf.append("..").append('/');
            }
        }

        for (; i < parts.length; i++) {
            buf.append(parts[i]);

            if (i < parts.length - 1) {
                buf.append('/');
            }
        }

        if (endsWithSlash && buf.length() > 0 && buf.charAt(buf.length() - 1) != '/') {
            buf.append('/');
        }

        return buf.toString();
    }

    /**
     * 取得文件路径的后缀。
     * <ul>
     * <li>未指定文件名 - 返回<code>null</code>。</li>
     * <li>文件名没有后缀 - 返回<code>null</code>。</li>
     * </ul>
     */
    public static String getExtension(String fileName) {
        return getExtension(fileName, null, false);
    }

    /**
     * 取得文件路径的后缀。
     * <ul>
     * <li>未指定文件名 - 返回<code>null</code>。</li>
     * <li>文件名没有后缀 - 返回<code>null</code>。</li>
     * </ul>
     */
    public static String getExtension(String fileName, boolean toLowerCase) {
        return getExtension(fileName, null, toLowerCase);
    }

    /**
     * 取得文件路径的后缀。
     * <ul>
     * <li>未指定文件名 - 返回<code>null</code>。</li>
     * <li>文件名没有后缀 - 返回指定字符串<code>nullExt</code>。</li>
     * </ul>
     */
    public static String getExtension(String fileName, String nullExt) {
        return getExtension(fileName, nullExt, false);
    }

    /**
     * 取得文件路径的后缀。
     * <ul>
     * <li>未指定文件名 - 返回<code>null</code>。</li>
     * <li>文件名没有后缀 - 返回指定字符串<code>nullExt</code>。</li>
     * </ul>
     */
    public static String getExtension(String fileName, String nullExt, boolean toLowerCase) {
        fileName = StringUtils.trimToNull(fileName);

        if (fileName == null) {
            return null;
        }

        fileName = fileName.replace('\\', '/');
        fileName = fileName.substring(fileName.lastIndexOf("/") + 1);

        int index = fileName.lastIndexOf(".");
        String ext = null;

        if (index >= 0) {
            ext = StringUtils.trimToNull(fileName.substring(index + 1));
        }

        if (ext == null) {
            return nullExt;
        } else {
            return toLowerCase ? ext.toLowerCase() : ext;
        }
    }

    /**
     * 取得指定路径的名称和后缀。
     *
     * @param path 路径
     * @return 路径和后缀
     */
    public static FileNameAndExtension getFileNameAndExtension(String path) {
        return getFileNameAndExtension(path, false);
    }

    /**
     * 取得指定路径的名称和后缀。
     *
     * @param path 路径
     * @return 路径和后缀
     */
    public static FileNameAndExtension getFileNameAndExtension(String path, boolean extensionToLowerCase) {
        path = StringUtils.trimToEmpty(path);

        String fileName = path;
        String extension = null;

        if (!StringUtils.isEmpty(path)) {
            // 如果找到后缀，则index >= 0，且extension != null（除非name以.结尾）
            int index = path.lastIndexOf('.');

            if (index >= 0) {
                extension = StringUtils.trimToNull(StringUtils.substring(path, index + 1));

                if (!StringUtils.containsNone(extension, "/\\")) {
                    extension = null;
                    index = -1;
                }
            }

            if (index >= 0) {
                fileName = StringUtils.substring(path, 0, index);
            }
        }

        return new FileNameAndExtension(fileName, extension, extensionToLowerCase);
    }

    /**
     * 规格化文件名后缀。
     * <ul>
     * <li>除去两边空白。</li>
     * <li>转成小写。</li>
     * <li>除去开头的“<code>.</code>”。</li>
     * <li>对空白的后缀，返回<code>null</code>。</li>
     * </ul>
     */
    public static String normalizeExtension(String ext) {
        ext = StringUtils.trimToNull(ext);

        if (ext != null) {
            ext = ext.toLowerCase();

            if (ext.startsWith(".")) {
                ext = StringUtils.trimToNull(ext.substring(1));
            }
        }

        return ext;
    }

    /**
     * 根据指定url和相对路径，计算出相对路径所对应的完整url。类似于<code>URI.resolve()</code>
     * 方法，然后后者不能正确处理jar类型的URL。
     */
    public static String resolve(String url, String relativePath) {
        url = StringUtils.trimToEmpty(url);

        Matcher m = schemePrefixPattern.matcher(url);
        int index = 0;

        if (m.find()) {
            index = m.end();

            if (url.charAt(index - 1) == '/') {
                index--;
            }
        }

        return url.substring(0, index) + normalizeAbsolutePath(url.substring(index) + "/../" + relativePath);
    }

    /**
     * http://blog.csdn.net/very365_1208/article/details/8824033
     * http://www.avajava.com/tutorials/lessons/whats-a-quick-way
     * -to-tell-if-the-contents-of-two-files-are-identical-or-not.html
     *
     * @param oldFile
     * @param newFile
     * @return
     */
    public static boolean isFileEqual(File oldFile, File newFile) {

        try {

            return contentEquals(oldFile, newFile);

        } catch (IOException e) {

            logger.warn(e.toString());
            return false;
        }

    }

    /**
     * 使用jar包：commons-codec-2.4.jar的md5比较方法 <br/>
     * http://blog.csdn.net/very365_1208/article/details/8824033
     *
     * @param oldName
     * @param newName
     * @return
     */
    public static boolean isFileUpdate(String oldName, String newName) {

        return isFileEqual(new File(oldName), new File(newName));
    }

    public static class FileNameAndExtension {
        private final String fileName;
        private final String extension;

        private FileNameAndExtension(String fileName, String extension, boolean extensionToLowerCase) {
            this.fileName = fileName;
            this.extension = extensionToLowerCase ? extension.toLowerCase() : extension;
        }

        public String getFileName() {
            return fileName;
        }

        public String getExtension() {
            return extension;
        }

        @Override
        public String toString() {
            return extension == null ? fileName : fileName + "." + extension;
        }
    }
}
