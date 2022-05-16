package com.kdt.boardMission.service;

import com.kdt.boardMission.domain.User;
import com.kdt.boardMission.dto.UserDto;
import com.kdt.boardMission.repository.UserRepository;
import javassist.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.awt.print.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    String DEFAULT_USER_NAME = "default user Name";
    int DEFAULT_USER_AGE = 20;
    String DEFAULT_USER_HOBBY = "default user hobby";
    @BeforeEach
    void setIp() {
        userRepository.deleteAll();

        UserDto userDto = new UserDto();
        userDto.setName(DEFAULT_USER_NAME);
        userDto.setAge(DEFAULT_USER_AGE);
        userDto.setHobby(DEFAULT_USER_HOBBY);
        USER_DEFAULT_ID = userService.saveUser(userDto);

    }

    long USER_DEFAULT_ID;

    @Test
    @DisplayName("user 저장 및 조회")
    public void saveTest() throws Exception {

        //given
        UserDto userDto = new UserDto();
        userDto.setName("userName");
        userDto.setAge(20);
        userDto.setHobby("nonononono");

        //when
        long userId = userService.saveUser(userDto);

        //then
        UserDto userById = userService.findUserById(userId);

        assertThat(userById.getName()).isEqualTo(userDto.getName());
        assertThat(userById.getAge()).isEqualTo(userDto.getAge());
        assertThat(userById.getHobby()).isEqualTo(userDto.getHobby());
    }

    @Test
    @DisplayName("유저 삭제")
    public void Test() throws Exception {

        //given
        UserDto userDto = new UserDto();
        userDto.setName("userName");
        userDto.setAge(20);
        userDto.setHobby("nonononono");

        long userId = userService.saveUser(userDto);
        UserDto userById = userService.findUserById(userId);
        assertThat(userById).isNotNull();

        //when
        userService.deleteUser(userById);

        //then
        assertThrows(NotFoundException.class, () -> userService.findUserById(userId));
    }

    @Test
    @DisplayName("이름으로 찾기")
    public void findNameTest() throws Exception {

        //given
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        Page<UserDto> byNamePage = userService.findUserByName("fault", pageRequest);

        //then
        assertThat(byNamePage.getSize()).isEqualTo(10);
        assertThat(byNamePage.getTotalElements()).isEqualTo(1);
        assertThat(byNamePage.getNumber()).isEqualTo(0);
        assertThat(byNamePage.getTotalPages()).isEqualTo(1);
        assertThat(byNamePage.isFirst()).isTrue();
        assertThat(byNamePage.hasNext()).isFalse();
        assertThat(byNamePage.stream().findFirst().get().getName()).isEqualTo(DEFAULT_USER_NAME);
    }

    @Test
    @DisplayName("전체 조회")
    public void findAllUserTest() throws Exception {

        //given
        for (int i = 0; i < 20; i++) {
            UserDto userDto = new UserDto();
            userDto.setName("userName" + i);
            userDto.setAge(20);
            userDto.setHobby("nonononono");
            userService.saveUser(userDto);
        }

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        Page<UserDto> all = userService.findAll(pageRequest);

        //then
        assertThat(all.getSize()).isEqualTo(10);
        assertThat(all.getTotalElements()).isEqualTo(21);
        assertThat(all.getNumber()).isEqualTo(0);
        assertThat(all.getTotalPages()).isEqualTo(3);
        assertThat(all.isFirst()).isTrue();
        assertThat(all.hasNext()).isTrue();
    }

    @Test
    @DisplayName("Hobby 업데이트")
    public void updateHobbyTest() throws Exception {

        //given
        UserDto userDto = new UserDto();
        userDto.setId(USER_DEFAULT_ID);
        userDto.setName(DEFAULT_USER_NAME);
        userDto.setAge(DEFAULT_USER_AGE);
        userDto.setHobby("new hobby");

        //when
        userService.updateUserHobby(userDto);

        //then
        assertThat(userService.findUserById(USER_DEFAULT_ID).getHobby()).isEqualTo("new hobby");
    }

}