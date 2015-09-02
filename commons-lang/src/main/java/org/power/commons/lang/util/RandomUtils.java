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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * //TODO insert into titile here
 *
 * @author Kuo Hong on 2015/9/2 0002.
 */
public final class RandomUtils {
    private RandomUtils() {

    }

    /**
     * 获取一堆数据
     *
     * @param limit
     * @return
     */
    public static List<Integer> randomSerial(int limit) {
        List<Integer> list = new ArrayList<Integer>(limit);

        for (int ix = 0; ix < limit; ++ix) {
            list.add(ix);
        }

        Collections.shuffle(list, new Random());
        return list;
    }

    public static void main(String[] args) {

        List<Integer> a = RandomUtils.randomSerial(10);
        for (int i : a) {
            System.out.println(i);
        }
        // for(int i = 0 ;i < 500 ; i++){
        // System.out.println(random(0, 20));
        // }
    }

    /**
     * [min,max]
     *
     * @param min
     * @param max
     * @return
     */
    public static int random(int min, int max) {
        if (min < 0 || max < 0) {
            throw new RuntimeException(
                    "illegal argment, min and max must great then zero.");
        }
        if (min > max) {
            int t = max;
            max = min;
            min = t;
        } else if (min == max) {
            return min;
        }

        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;

    }
}
