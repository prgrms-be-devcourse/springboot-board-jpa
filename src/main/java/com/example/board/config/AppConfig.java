package com.example.board.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class AppConfig {
    public CacheManager cacheManager() {
        return new SimpleCacheManager() {{
            setCaches(Arrays.asList(
                    new ConcurrentMapCache("users")
            ));
        }};
    }
}
