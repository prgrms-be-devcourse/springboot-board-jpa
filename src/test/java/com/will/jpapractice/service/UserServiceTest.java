package com.will.jpapractice.service;

import com.will.jpapractice.domain.user.application.UserService;
import com.will.jpapractice.domain.user.domain.User;
import com.will.jpapractice.domain.user.dto.UserRequest;
import com.will.jpapractice.domain.user.dto.UserResponse;
import com.will.jpapractice.domain.user.repository.UserRepository;
import com.will.jpapractice.global.converter.Converter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Converter converter;

    @Mock
    User user;


    @Test
    @DisplayName("새로운 user을 생성한다.")
    void test_create_user() {
        UserRequest request = new UserRequest("will", 22);
        given(converter.toUser(request)).willReturn(user);
        given(userRepository.save(user)).willReturn(user);
        given(user.getId()).willReturn(1L);

        Long id = userService.save(request);

        then(userRepository).should().save(user);
        assertThat(id).isEqualTo(1L);
    }


    @Test
    @DisplayName("user 페이지로 조회한다.")
    void test_find_pageable_user() {
        Pageable pageable = PageRequest.of(0, 10);
        List<User> users = new ArrayList<>();
        for (int i=0; i<20; i++) {
            users.add(
                    User.builder().build()
            );
        }
        given(userRepository.findAll(pageable)).willReturn(new PageImpl<>(users));

        Page<UserResponse> findPosts = userService.findUsers(pageable);

        assertThat(findPosts).hasSize(20);
    }

}
