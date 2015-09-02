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

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis Operation interface
 *
 * @author Kuo Hong
 * @version 2015-09-02
 */
public interface RedisOperation {
    /**
     * get
     *
     * @param key
     * @return
     * @throws Exception
     */
    Object get(String key) throws Exception;

    /**
     * set with expiration
     *
     * @param key
     * @param value
     * @param expiration
     * @return
     * @throws Exception
     */
    boolean set(String key, Object value, Integer expiration) throws Exception;

    /**
     * set with no expiration
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    boolean set(String key, Object value) throws Exception;

    /**
     * add with expiration by leveraging setnx
     *
     * @param key
     * @param value
     * @param expiration
     * @return
     * @throws Exception
     */
    boolean add(String key, Object value, Integer expiration) throws Exception;

    /**
     * add by leveraging setnx
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    boolean add(String key, Object value) throws Exception;

    /**
     * exists
     *
     * @param key
     * @return
     * @throws Exception
     */
    boolean exists(String key) throws Exception;

    /**
     * delete
     *
     * @param key
     * @return
     */
    boolean delete(String key);

    /**
     * expire
     *
     * @param key
     * @param seconds
     * @return
     */
    boolean expire(String key, int seconds);

    /**
     * hash put
     *
     * @param key
     * @param field
     * @param fieldValue
     * @throws Exception
     */
    void hput(String key, String field, Serializable fieldValue) throws Exception;

    /**
     * hash get
     *
     * @param key
     * @param field
     * @return
     */
    Object hget(String key, String field);

    /**
     * hash del
     *
     * @param key
     * @param field
     * @return
     * @throws Exception
     */
    boolean hdel(String key, String field) throws Exception;

    /**
     * hash keys
     *
     * @param key
     * @return
     * @throws Exception
     */
    Set<String> hKeys(String key) throws Exception;

    /**
     * hash values
     *
     * @param key
     * @return
     * @throws Exception
     */
    List<Object> hValues(String key) throws Exception;

    /**
     * hash exsits
     *
     * @param key
     * @param field
     * @return
     * @throws Exception
     */
    boolean hExists(String key, String field) throws Exception;

    /**
     * hash length
     *
     * @param key
     * @return
     * @throws Exception
     */
    long hLen(String key) throws Exception;

    /**
     * hash get all
     *
     * @param key
     * @return
     * @throws Exception
     */
    Map<String, Object> hGetAll(String key) throws Exception;

    /**
     * hash multiple set
     *
     * @param key
     * @param values
     * @throws Exception
     */
    void hmSet(String key, Map<String, Serializable> values) throws Exception;

    /**
     * hash multiple get
     *
     * @param key
     * @param fields
     * @return
     * @throws Exception
     */
    List<Object> hmGet(String key, String... fields) throws Exception;

    /**
     * hash multiple get by using basic string serializer
     *
     * @param key
     * @param fields
     * @return
     * @throws Exception
     */
    List<String> hmGetByStringSerializer(String key, String... fields) throws Exception;

    /**
     * hash multiple set by using basic string serializer
     *
     * @param key
     * @param values
     * @throws Exception
     */
    void hmSetByStringSerializer(String key, Map<String, String> values) throws Exception;

    /**
     * set add
     *
     * @param key
     * @param member
     * @return
     * @throws Exception
     */
    boolean sAdd(String key, String member) throws Exception;

    /**
     * set remove
     *
     * @param key
     * @param member
     * @return
     * @throws Exception
     */
    boolean sRem(String key, String member) throws Exception;

    /**
     * set members
     *
     * @param key
     * @return
     * @throws Exception
     */
    Set<String> sMembers(String key) throws Exception;

    /**
     * list push
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    boolean lpush(String key, Object value) throws Exception;

    /**
     * list pop
     *
     * @param key
     * @param cls
     * @return
     * @throws Exception
     */
    Object lpop(String key, Class<?> cls) throws Exception;

    /**
     * reverse list push
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    boolean rpush(String key, Object value) throws Exception;

    /**
     * reverse list pop
     *
     * @param key
     * @param cls
     * @return
     * @throws Exception
     */
    Object rpop(String key, Class<?> cls) throws Exception;

    /**
     * incr
     *
     * @param key
     * @return
     * @throws Exception
     */
    Long incr(String key) throws Exception;

    /**
     * incrBy
     *
     * @param key
     * @param integer
     * @return
     * @throws Exception
     */
    Long incrBy(final String key, final long integer) throws Exception;
}
