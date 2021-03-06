/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.profiler.modifier.arcus.interceptor;

import com.navercorp.pinpoint.bootstrap.interceptor.SimpleAroundInterceptor;
import com.navercorp.pinpoint.bootstrap.interceptor.TargetClassLoader;
import com.navercorp.pinpoint.bootstrap.logging.PLogger;
import com.navercorp.pinpoint.bootstrap.logging.PLoggerFactory;
import com.navercorp.pinpoint.bootstrap.util.MetaObject;

import net.spy.memcached.CacheManager;
import net.spy.memcached.MemcachedClient;

/**
 * 
 * @author netspider
 * @author emeroad
 */
public class SetCacheManagerInterceptor implements SimpleAroundInterceptor, TargetClassLoader {

    private final PLogger logger = PLoggerFactory.getLogger(this.getClass());
    private final boolean isDebug = logger.isDebugEnabled();

    private MetaObject<String> getServiceCode = new MetaObject<String>("__getServiceCode");
    private MetaObject<String> setServiceCode = new MetaObject<String>("__setServiceCode", String.class);

    @Override
    public void before(Object target, Object[] args) {
        if (isDebug) {
            logger.beforeInterceptor(target, args);
        }

        CacheManager cm = (CacheManager) args[0];
        String serviceCode = getServiceCode.invoke(cm);

        setServiceCode.invoke((MemcachedClient) target, serviceCode);
    }

    @Override
    public void after(Object target, Object[] args, Object result, Throwable throwable) {

    }
}
