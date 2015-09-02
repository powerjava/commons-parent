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

package org.power.commons.redis;

import java.util.List;

/**
 * Redis Client CallBack interface
 *
 * @author Kuo Hong
 * @version 2015-09-02
 */
public interface RedisCallBack<T> {
    /**
     * 具体操作实现接口
     *
     * @param clients
     * @param isRead  是否为只读，true：查询到非空结果即返回，false：双写策略
     * @param key
     * @return boolean
     */
    boolean doInRedis(List<RedisClient> clients, boolean isRead, Object key);

    /**
     * 操作类型
     *
     * @return String
     */
    String getOptionType();

    /**
     * 返回结果
     *
     * @return T
     */
    T getResult();

    /**
     * 返回异常
     *
     * @return Exception
     */
    Exception getException();
}
