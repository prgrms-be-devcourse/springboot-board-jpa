package yjh.jpa.springnoticeboard.domain.service;

import javassist.NotFoundException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    private static UserDto userDto;

    @BeforeEach
    void insert() throws NotFoundException {
        var userDtoB = UserDto.builder()
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
    @DisplayName("Post 저장 및 조회 테스트")
    void createPost() throws NotFoundException {
        //given
        PostDto postDto = PostDto.builder()
                .user(userDto)
                .title("게시판 제목")
                .content("블라블라블라")
                .build();
        //when
        Long postId = postService.createPost(postDto);

        //then
        User findUser = userRepository.findById(userDto.getId()).get();
        Post findPost = postRepository.findById(findUser.getPosts().get(0).getId()).get();
        assertThat(findPost.getId()).isEqualTo(postId);
        assertThat(findUser.getPosts()).isNotEmpty();
        assertThat(findUser.getPosts().get(0).getContent()).isEqualTo(findPost.getContent());
    }

    @Test
    @DisplayName("Post 업데이트 테스트")
    void updatePost() throws NotFoundException {
        //given
        PostDto postDto = PostDto.builder()
                .user(userDto)
                .title("게시판 제목")
                .content("블라블라블라")
                .build();
        Long postId = postService.createPost(postDto);

        //when
        PostDto findPost = postService.findPost(postId);
        findPost.setTitle("업데이트한 게시판 제목");
        findPost.setContent("업데이트한 블라블라블라");

        //then
        PostDto updatePost = postService.findPost(postId);
        log.info(updatePost.getTitle(), updatePost.getContent());
    }

    @Test
    @DisplayName("모든 게시글 불러오기")
    void findAll() throws NotFoundException {
        //given
        PostDto postDto = PostDto.builder()
                .user(userDto)
                .title("게시판 제목")
                .content("블라블라블라")
                .build();
        PostDto postDto2 = PostDto.builder()
                .user(userDto)
                .title("게시판 제목2")
                .content("블라블라블라2")
                .build();
        Long postId1 = postService.createPost(postDto);
        Long postId2 = postService.createPost(postDto2);

        //when
        Long postSize = postService.findAll(Pageable.ofSize(100)).getTotalElements();

        //then
        assertThat(postSize).isEqualTo(2);
    }

    @Test
    @DisplayName("특정 post 삭제하기")
    void deletePost() throws NotFoundException {
        //given
        PostDto postDto = PostDto.builder()
                .user(userDto)
                .title("게시판 제목")
                .content("블라블라블라")
                .build();

//        log.info("user 확인 : {}", userDto.toString());

        Long postId = postService.createPost(postDto);

        PostDto post = postService.findPost(postId);

        //when
        var findUser = userRepository.findById(userDto.getId()).orElseThrow(() ->
                new NotFoundException("회원을 찾을 수 없습니다."));
        postService.deletePost(postId,userDto.getId());

        //then

        assertThatThrownBy(()->postService.findPost(postId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("게시글을 찾을 수 없습니다.");
        assertThat(findUser.getPosts().size()).isEqualTo(0);
    }

}