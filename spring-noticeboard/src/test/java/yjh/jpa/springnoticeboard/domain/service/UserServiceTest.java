package yjh.jpa.springnoticeboard.domain.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import yjh.jpa.springnoticeboard.domain.converter.PostMapper;
import yjh.jpa.springnoticeboard.domain.converter.UserMapper;
import yjh.jpa.springnoticeboard.domain.dto.PostDto;
import yjh.jpa.springnoticeboard.domain.dto.UserDto;
import yjh.jpa.springnoticeboard.domain.entity.Post;
import yjh.jpa.springnoticeboard.domain.entity.User;
import yjh.jpa.springnoticeboard.domain.repository.PostRepository;
import yjh.jpa.springnoticeboard.domain.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    private static UserDto userDto;

    @BeforeEach
    void insert() throws NotFoundException {
        UserDto userDtoB = UserDto.builder()
                .age(20)
                .name("유지훈")
                .hobby("잠자기")
                .build();
        Long userId = userService.createUser(userDtoB);
        userDto = userService.findUser(userId);
    }

    @AfterEach
    void tearDown(){
        postRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("사용자 생성 및 조회 테스트")
    void createUser() throws NotFoundException {
        //given
        UserDto userDto2 = UserDto.builder()
                .id(1L)
                .age(23)
                .name("유지훈2")
                .hobby("잠자기2")
                .build();
        //when
        Long id =userService.createUser(userDto2);

        //then
        UserDto findUser = userService.findUser(id);
        assertThat(findUser.getId()).isEqualTo(id);
        assertThat(findUser.getName()).isEqualTo(userDto2.getName());
        assertThat(findUser.getAge()).isEqualTo(userDto2.getAge());
        assertThat(findUser.getHobby()).isEqualTo(userDto2.getHobby());
    }

    @Test
    @DisplayName("사용자 업데이트 기능 테스트")
    void updateUser() throws NotFoundException {
        //given
        //when
        UserDto findUser = userService.findUser(userDto.getId());
        findUser.setName("update 유지훈");
        findUser.setHobby("update 취미");
        findUser.setAge(25);
        Long updateId = userService.updateUser(findUser.getId(), findUser);

        //then
        UserDto updateUser = userService.findUser(updateId);
        assertThat(updateUser.getId()).isEqualTo(findUser.getId());
        log.info("업데이트 사용자 이름 {}",updateUser.getName());
        log.info("업데이트 사용자 취미 {}",updateUser.getHobby());
        log.info("업데이트 사용자 나이 {}",updateUser.getAge());
    }

    @Test
    @Rollback(value = false)
    @DisplayName("사용자 delete시 관련된 모든객체 삭제 기능 테스트")
    void deleteUser() throws NotFoundException {
        //given
        var findUser = userRepository.findById(userDto.getId()).orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));

        //when
        userService.deleteUser(userDto.getId());

        //then
        assertThatThrownBy(()->userService.findUser(userDto.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("해당 유저를 찾을 수 없습니다.");
    }

}