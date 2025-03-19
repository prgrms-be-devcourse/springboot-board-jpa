package com.example.board.repository;

import com.example.board.model.PostCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostCounterRedisRepository {

    private final RedisTemplate<Long, PostCounter> redisTemplate;

    public Optional<PostCounter> getPostCounter(Long userId){
        PostCounter postCounter = redisTemplate.opsForValue().get(userId);
        return Optional.ofNullable(postCounter);
    }

    public void savePostCounter(Long userId, PostCounter postCounter){
        redisTemplate.opsForValue().set(userId, postCounter);
    }
}
