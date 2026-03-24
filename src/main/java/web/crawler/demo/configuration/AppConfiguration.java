package web.crawler.demo.configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class AppConfiguration {

    @Autowired
    private AppProperties appProperties;

    @Bean
    @SuppressWarnings("null")
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(appProperties.getCacheExpirationSeconds(), TimeUnit.SECONDS)
                .maximumSize(10));
        cacheManager.setCacheNames(Arrays.asList("jsoupDocuments"));
        return cacheManager;
    }

}