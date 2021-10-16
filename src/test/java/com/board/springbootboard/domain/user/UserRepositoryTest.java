package com.board.springbootboard.domain.user;

import com.board.springbootboard.domain.posts.PostsEntity;
import com.board.springbootboard.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class UserRepositoryTest {


    @Autowired
    UserRepository userRepository;

    @After
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("유저 저장 테스트")
    public void UserSaveTest() {
        //Given
        String name="sds";
        String nickName="sds1zzang";
        int age=30;

        userRepository.save(UserEntity.builder()
                .name(name)
                .nickName(nickName)
                .age(age)
                .build());

        //When
        List<UserEntity> userList=userRepository.findAll();

        //Then
        UserEntity user=userList.get(0);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getNickName()).isEqualTo(nickName);

    }

    @Test
    @DisplayName("BaseEntity테스트")
    public void BaseEntity테스트() {
        // Given
        LocalDateTime now=LocalDateTime.of(2020,11,1,0,0,0);

        userRepository.save(UserEntity.builder()
                .name("sds")
                .nickName("sds1zzang")
                .age(30)
                .build());

        // When
        List<UserEntity> userList=userRepository.findAll();

        // Then
        UserEntity user=userList.get(0);
        log.info("createDate= {}, modifiedDate={}",user.getCreatedDate(),user.getModifiedDate());

        assertThat(user.getCreatedDate()).isAfter(now);
        assertThat(user.getModifiedDate()).isAfter(now);

    }

}
