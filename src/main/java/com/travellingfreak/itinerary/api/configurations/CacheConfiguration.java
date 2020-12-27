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

package com.travellingfreak.itinerary.api.configurations;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.google.common.collect.Lists.newArrayList;

/**
 * User: AustPC Date: 7/28/2018 Time: 11:12 PM
 *
 * @author AustPC
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

  public static final String TRAVEL_PERMISSIONS = "travel_permissions";
  public static final String JWT_TOKEN = "jwt_authentication";
  public static final String AUTHORITY = "authority_authentication";
  public static final String NAMES = "member_names";
  public static final String RATES = "rates";
  public static final String AIRPORTS = "airports";
  public static final String DIRECTIONS = "jorudan_directions";

  @Bean
  public CacheManager cacheManager() {
    final SimpleCacheManager cacheManager = new SimpleCacheManager();
    final CaffeineSpec shortLived = CaffeineSpec.parse("maximumSize=100,expireAfterWrite=15m");
    final CaffeineSpec longLived = CaffeineSpec.parse("maximumSize=100,expireAfterWrite=1d");
    cacheManager.setCaches(
        newArrayList(
            new CaffeineCache(TRAVEL_PERMISSIONS, Caffeine.from(shortLived).build()),
            new CaffeineCache(JWT_TOKEN, Caffeine.from(longLived).build()),
            new CaffeineCache(AUTHORITY, Caffeine.from(longLived).build()),
            new CaffeineCache(NAMES, Caffeine.from(longLived).build()),
            new CaffeineCache(RATES, Caffeine.from(longLived).build()),
            new CaffeineCache(AIRPORTS, Caffeine.from(longLived).build()),
            new CaffeineCache(DIRECTIONS, Caffeine.from(longLived).build())
        )
    );
    return cacheManager;
  }

}
