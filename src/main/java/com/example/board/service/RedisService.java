package com.example.board.service;

import com.example.board.model.PostCounter;
import com.example.board.model.User;
import com.example.board.repository.PostCounterRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final PostCounterRedisRepository redisRepository;

    public void postInfoChecking(User user){
        redisRepository.getPostCounter(user.getId())
                .ifPresentOrElse(
                        postCounter -> updateSavedData(postCounter, user.getId()),
                        () -> redisRepository.savePostCounter(
                        user.getId(),
                        PostCounter.firstPostInDay(user.getId())));
    }

    private void updateSavedData(PostCounter postCounter, Long userId){
        postCounter.update();
        redisRepository.savePostCounter(userId, postCounter);
    }


}
