package com.kdt.jpaboard.domain.board.user.service;

import com.kdt.jpaboard.domain.board.user.dto.CreateUserDto;
import com.kdt.jpaboard.domain.board.user.dto.UserDto;
import javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@DisplayName("유저 service 테스트")
class UserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private UserService userService;

    private CreateUserDto userDto;

    @BeforeEach
    void setup() {

        userDto = CreateUserDto.builder()
                .name("beomsic")
                .age(26)
                .hobby("soccer")
                .build();
    }

    @AfterEach
    void reset() {
        userService.deleteAll();
    }

    @Test
    @DisplayName("사용자 작성 테스트")
    void testSave() throws NotFoundException {
        // Given

        // When
        Long save = userService.save(userDto);

        // Then
        UserDto findUser = userService.findById(save);
        assertThat(findUser.getId().equals(save), is(true));
    }

    @Test
    @DisplayName("사용자 전체 조회 테스트")
    void testFindAll() throws NotFoundException {
        // Given
        PageRequest page = PageRequest.of(0, 10);

        // When
        userService.save(userDto);
        Page<UserDto> all = userService.findAll(page);

        // Then
        assertThat(all.getTotalElements() == 1, is(true));
    }

    @Test
    @DisplayName("사용자 단건 조회 테스트")
    void testFindOne() throws NotFoundException {
        // Given

        // When
        Long save = userService.save(userDto);
        UserDto user = userService.findById(save);

        // Then
        assertThat(save.equals(user.getId()), is(true));
        assertThat(user.getName().equals("beomsic"), is(true));
    }

    @Test
    @DisplayName("사용자 정보 수정 테스트")
    void testUpdate() throws NotFoundException {
        // Given
        CreateUserDto updateUser = CreateUserDto.builder()
                .name("beomsic")
                .age(20)
                .hobby("sleep")
                .build();

        // When
        Long save = userService.save(userDto);
        Long update = userService.update(save, updateUser);

        // Then
        UserDto userId = userService.findById(save);
        assertThat(userId.getHobby().equals("sleep"), is(true));
        assertThat(Objects.equals(save, update), is(true));
    }

    @Test
    @DisplayName("사용자 삭제 테스트")
    void testDelete() {
        // Given

        // When
        Long save = userService.save(userDto);
        userService.deleteById(save);

        // Then
        assertThrows(NotFoundException.class, () -> userService.findById(save));
    }
}