package com.kdt.user.service;

import com.kdt.user.dto.UserDto;
import com.kdt.user.repository.UserRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Slf4j
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private Long userId;
    private UserDto userDto;

    @BeforeEach
    void setUp(){
        userDto = UserDto.builder()
                .name("son")
                .age(30)
                .hobby("soccer")
                .build();
        userId = userService.save(userDto);
    }

    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자가 정상적으로 조회되는지 확인한다.")
    void findTest() throws NotFoundException {
        //Given
        //When
        UserDto user = userService.find(userId);

        //Then
        assertThat(userDto, samePropertyValuesAs(user, "postDtos", "createdAt", "createdBy", "lastUpdatedAt"));
        log.info(user.toString());
    }

    @Test
    @DisplayName("가입되지 않은 사용자 조회를 요청하면 예외가 발생한다")
    void findNonExistingUserTest() throws NotFoundException {
        //Given
        //When
        //Then
        assertThrows(NotFoundException.class, () -> userService.find(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("사용자 정보가 정상적으로 수정되는지 확인한다.")
    void updateTest() throws NotFoundException {
        //Given
        UserDto user = userService.find(userId);

        //When
        //sleep for checking lastUpdatedAt
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        user.setName("julie");
        user.setHobby("planting");
        userService.update(userId, user);

        //Then
        UserDto savedUser = userService.find(userId);
        assertThat(savedUser, samePropertyValuesAs(user, "lastUpdatedAt"));
        log.info(savedUser.toString());
    }

    @Test
    @DisplayName("사용자가 정상적으로 삭제되는지 확인한다.")
    void deleteTest() throws NotFoundException {
        //Given
        //When
        userService.delete(userId);

        //Then
        assertThrows(NotFoundException.class, () -> userService.find(userId));
    }
}