package org.project.cachingproxytool;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class CachingService {

    private final Cache<String, ResponseEntity<byte[]>> cache;

    public CachingService() {
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(15))   // expires after fifteen minutes
                .maximumSize(500)   // holds only the last 500 responses in memory.
                .build();
    }

    public String generateKey(HttpServletRequest request) {
        return request.getMethod() + ":" + request.getRequestURI() + "?" + request.getQueryString();
    }

    // get values from cache
    public ResponseEntity<byte[]> get(String key) {
        return cache.getIfPresent(key);
    }

    // put values in cache
    public void put(String key, ResponseEntity<byte[]> value) {
        cache.put(key, value);
        System.out.println("Cache successfully updated!");
    }

    public void clear() {
        cache.cleanUp();
        System.out.println("Cache cleared!");
    }
}
