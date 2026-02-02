package com.mpcz.deposit_scheme.backend.api.builddesk.config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CacheStore<T> {
	private Cache<String, List<T>> cache;

    public CacheStore(int expiryDuration, TimeUnit timeUnit) {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiryDuration, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    public List<T> get(String key) {
        return cache.getIfPresent(key);
    }

    public void add(String key, List<T> value) {
        if(key != null && value != null) {
            cache.put(key, value);
            System.out.println("Record stored in "
                    + value.getClass().getSimpleName()
                    + " Cache with Key = " + key);
        }
    }
}
