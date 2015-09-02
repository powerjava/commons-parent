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

package org.power.commons.redis.support;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * EvictionTimer
 *
 * @author Kuo Hong
 * @version 2015-09-02
 */
public class EvictionTimer {
    private static Timer timer;
    private static AtomicInteger usageCount = new AtomicInteger(0);

    private EvictionTimer() {
        // Hide the default constuctor
    }

    /**
     * Add the specified eviction task to the timer. Tasks that are added with a call to this method *must* call
     * {@link #cancel(java.util.TimerTask)} to cancel the task to prevent memory and/or thread leaks in application
     * server
     * environments.
     *
     * @param task                      Task to be scheduled
     * @param evictorDelayCheckSeconds  Delay in milliseconds before task is executed
     * @param evictorCheckPeriodSeconds Time in milliseconds between executions
     */
    public static synchronized void schedule(TimerTask task, int evictorDelayCheckSeconds,
                                             int evictorCheckPeriodSeconds) {
        if (null == timer) {
            timer = new Timer(true);
        }
        usageCount.incrementAndGet();
        timer.schedule(task, evictorDelayCheckSeconds * 1000, evictorCheckPeriodSeconds * 1000);
    }

    /**
     * Remove the specified eviction task from the timer.
     *
     * @param task Task to be scheduled
     */
    public static synchronized void cancel(TimerTask task) {
        if (task == null) {
            return;
        }
        task.cancel();
        usageCount.decrementAndGet();
        if (usageCount.get() == 0) {
            timer.cancel();
            timer = null;
        }
    }
}
