package com.example.board.config;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.example.board.domain.hobby.entity.Hobby;
import com.example.board.domain.hobby.entity.HobbyType;
import com.example.board.domain.hobby.repository.HobbyRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitData {

    private final HobbyRepository hobbyRepository;

    @PostConstruct
    private void initHobbyData() {
        hobbyRepository.saveAllAndFlush(Arrays.stream(HobbyType.values())
                .map(Hobby::new).toList());
    }
}
