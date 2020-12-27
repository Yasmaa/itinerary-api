/*
 * The MIT License (MIT)
 * Copyright (c) 2018 Michele Masili
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.travellingfreak.itinerary.api.core.caches;

import com.google.common.util.concurrent.UncheckedExecutionException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static com.google.common.collect.Lists.newArrayList;

/**
 * User: AustPC Date: 7/29/2018 Time: 9:26 AM
 *
 * @author AustPC
 */
@Aspect
@Component
public class CachedAspect {

  private final CacheManager cacheManager;

  public CachedAspect(final CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  @Around("@annotation(com.travellingfreak.itinerary.api.core.caches.Cached)")
  public Object applyCache(final ProceedingJoinPoint call) throws Throwable {
    final MethodSignature signature = (MethodSignature) call.getSignature();
    final Method method = signature.getMethod();

    final Cached cached = method.getAnnotation(Cached.class);
    final String name = cached.name();
    final Cache cache = cacheManager.getCache(name);
    if ( cache == null ) {
      return call.proceed();
    } else {
      return cache.get(newArrayList(call.getArgs()), () -> {
        try {
          return call.proceed();
        } catch (final Throwable throwable) {
          throw new UncheckedExecutionException(throwable);
        }
      });
    }
  }
}
