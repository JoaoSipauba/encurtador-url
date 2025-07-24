package com.company.tds.encurtador_url.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.DAYS;

@Configuration
@EnableCaching
public class CacheConfig {

    @SuppressWarnings("rawtypes")
    @Bean(name = "defaultCaffeineConfig")
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(1, DAYS)
                .maximumSize(200);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Bean(name = "cacheDefaultManager")
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}
