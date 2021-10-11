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

    private static User user;

    @BeforeEach
    void insert(){
        UserDto userDto = UserDto.builder()
                .age(20)
                .name("유지훈")
                .hobby("잠자기")
                .build();
        Long userId = userService.createUser(userDto);
        user = userRepository.getById(userId);
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
                .age(23)
                .name("유지훈2")
                .hobby("잠자기2")
                .build();
        //when
        Long userId =userService.createUser(userDto2);

        //then
        UserDto findUser = userService.findUser(userId);
        assertThat(findUser.getId()).isEqualTo(userId);
        assertThat(findUser.getName()).isEqualTo(userDto2.getName());
        assertThat(findUser.getAge()).isEqualTo(userDto2.getAge());
        assertThat(findUser.getHobby()).isEqualTo(userDto2.getHobby());
    }

    @Test
    @DisplayName("사용자 업데이트 기능 테스트")
    void updateUser() throws NotFoundException {
        //given
        PostDto postDto = PostDto.builder()
                .user(user)
                .title("게시판 제목")
                .content("블라블라블라")
                .build();
        Long postId = postService.createPost(postDto);
        Post post = PostMapper.INSTANCE.postDtoToEntity(postService.findPost(postId));

        //when
        UserDto findUser = userService.findUser(user.getId());
        findUser.setName("update 유지훈");
        findUser.setHobby("update 취미");
        findUser.setAge(25);
        findUser.getPosts().add(post);
        long updateId = userService.updateUser(findUser.getId(), findUser);

        //then
        UserDto updateUser = userService.findUser(updateId);
        assertThat(updateUser.getId()).isEqualTo(findUser.getId());
        log.info("업데이트 사용자 이름 {}",updateUser.getName());
        log.info("업데이트 사용자 취미 {}",updateUser.getHobby());
        log.info("업데이트 사용자 나이 {}",updateUser.getAge());
        log.info("업데이트 사용자 게시글 갯 수 {}",updateUser.getPosts().size());
    }

    @Test
    @DisplayName("사용자 delete시 관련된 모든객체 삭제 기능 테스트")
    void deleteUser() throws NotFoundException {
        //given
        PostDto postDto = PostDto.builder()
                .user(user)
                .title("게시판 제목")
                .content("블라블라블라")
                .build();
        Long postId = postService.createPost(postDto);
        Post post = PostMapper.INSTANCE.postDtoToEntity(postService.findPost(postId));
        var findUser = userService.findUser(user.getId());
        findUser.getPosts().add(post);

        //when
        userService.deleteUser(user.getId());

        //then
        assertThatThrownBy(()->userService.findUser(user.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("해당 유저를 찾을 수 없습니다.");
    }

}