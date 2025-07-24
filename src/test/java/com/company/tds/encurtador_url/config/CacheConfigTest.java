package com.company.tds.encurtador_url.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.jupiter.api.Test;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import static org.junit.jupiter.api.Assertions.*;

class CacheConfigTest {

    @Test
    void criarCaffeineComConfiguracaoPadrao() {
        CacheConfig cacheConfig = new CacheConfig();
        Caffeine caffeine = cacheConfig.caffeineConfig();

        assertNotNull(caffeine);
    }

    @Test
    void criarCacheManagerComConfiguracaoPadrao() {
        CacheConfig cacheConfig = new CacheConfig();
        Caffeine caffeine = cacheConfig.caffeineConfig();
        CacheManager cacheManager = cacheConfig.cacheManager(caffeine);

        assertNotNull(cacheManager);
        assertTrue(cacheManager instanceof CaffeineCacheManager);
    }
}