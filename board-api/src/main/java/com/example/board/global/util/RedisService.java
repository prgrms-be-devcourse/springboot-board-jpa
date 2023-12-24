package com.example.board.global.util;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {

    private final StringRedisTemplate template;

    public String getData(String key) {
        ValueOperations<String, String> valueOperations = template.opsForValue();
        return valueOperations.get(key);
    }

    public boolean existData(String key) {
        return Boolean.TRUE.equals(template.hasKey(key));
    }

    public void setEmail(String key, String value, long duration, String purpose) {
        ValueOperations<String, String> valueOperations = template.opsForValue();
        Duration expireDuration = Duration.ofMillis(duration);
        String keyWithPurpose = key + ":" + purpose;
        log.info("set Email with key = {} , value = {} , expireDuration = {}", keyWithPurpose, value, expireDuration);
        valueOperations.set(keyWithPurpose, value, expireDuration);
    }

    public void setRefresh(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = template.opsForValue();
        Duration expireDuration = Duration.ofMillis(duration);
        log.info("set Refresh with key = {} , value = {} , expireDuration = {}", key, value, expireDuration);
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteEmail(String key, String purpose) {
        String keyWithPurpose = key + ":" + purpose;
        log.info("delete Email From Redis key = {} , purpose = {}", keyWithPurpose, purpose);
        template.delete(keyWithPurpose);
    }
}

