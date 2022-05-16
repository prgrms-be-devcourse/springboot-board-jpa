package com.kdt.boardMission.service;

import com.kdt.boardMission.dto.PostDto;
import com.kdt.boardMission.dto.UserDto;
import com.kdt.boardMission.repository.PostRepository;
import com.kdt.boardMission.repository.UserRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    String DEFAULT_USER_NAME = "default user Name";
    int DEFAULT_USER_AGE = 20;
    String DEFAULT_USER_HOBBY = "default user hobby";

    @BeforeEach
    void setIp() {
        userRepository.deleteAll();
        postRepository.deleteAll();

        UserDto userDto = new UserDto();
        userDto.setName(DEFAULT_USER_NAME);
        userDto.setAge(DEFAULT_USER_AGE);
        userDto.setHobby(DEFAULT_USER_HOBBY);
        USER_DEFAULT_ID = userService.saveUser(userDto);
    }

    long USER_DEFAULT_ID;

    @Test
    @DisplayName("게시물 저장")
    public void savePostTest() throws Exception {

        //given
        UserDto userDto = userService.findUserById(USER_DEFAULT_ID);
        PostDto postDto = new PostDto();
        postDto.setTitle("title");
        postDto.setContent("content");

        //when
        long postId = postService.savePost(postDto, userDto);

        //then
        assertThat(postService.findById(postId).getTitle()).isEqualTo(postDto.getTitle());
        assertThat(postService.findById(postId).getContent()).isEqualTo(postDto.getContent());
        assertThat(postService.findById(postId).getUser().getId()).isEqualTo(userDto.getId());
    }

    @Test
    @DisplayName("타이틀 업데이트")
    public void updateTitleTest() throws Exception {

        //given
        UserDto userDto = userService.findUserById(USER_DEFAULT_ID);
        PostDto postDto = new PostDto();
        postDto.setTitle("title");
        postDto.setContent("content");
        long postId = postService.savePost(postDto, userDto);
        PostDto postDtoById = postService.findById(postId);

        //when
        postDtoById.setTitle("new title");
        postService.updateTitle(postDtoById);

        //then
        assertThat(postService.findById(postId).getTitle()).isEqualTo("new title");
    }


    @Test
    @DisplayName("타이틀 업데이트")
    public void updateContentTest() throws Exception {

        //given
        UserDto userDto = userService.findUserById(USER_DEFAULT_ID);
        PostDto postDto = new PostDto();
        postDto.setTitle("title");
        postDto.setContent("content");
        long postId = postService.savePost(postDto, userDto);
        PostDto postDtoById = postService.findById(postId);

        //when
        postDtoById.setContent("new content");
        postService.updateContent(postDtoById);

        //then
        assertThat(postService.findById(postId).getContent()).isEqualTo("new content");
    }

    @Test
    @DisplayName("게시물 삭제")
    public void deletePostTest() throws Exception {

        //given
        UserDto userDto = userService.findUserById(USER_DEFAULT_ID);
        PostDto postDto = new PostDto();
        postDto.setTitle("title");
        postDto.setContent("content");
        long postId = postService.savePost(postDto, userDto);
        PostDto postDtoById = postService.findById(postId);

        //when
        postService.deletePost(postDtoById);

        //then
        Assertions.assertThrows(NotFoundException.class, () -> postService.findById(postId));
    }

    @Test
    @DisplayName("타이틀로 검색")
    public void findByTitleTest() throws Exception {

        //given
        UserDto userDto = userService.findUserById(USER_DEFAULT_ID);
        for (int i = 0; i < 10; i++) {
            PostDto postDto = new PostDto();
            postDto.setTitle("title" + i);
            postDto.setContent("content");
            long postId = postService.savePost(postDto, userDto);
        }

        for (int i = 0; i < 10; i++) {
            PostDto postDto = new PostDto();
            postDto.setTitle("diff title" + i);
            postDto.setContent("content");
            long postId = postService.savePost(postDto, userDto);
        }

        PageRequest pageRequest = PageRequest.of(0, 5);

        //when
        Page<PostDto> pagePostDtoByTitle = postService.findByTitle("dif", pageRequest);

        //then
        assertThat(pagePostDtoByTitle.getSize()).isEqualTo(5);
        assertThat(pagePostDtoByTitle.getTotalElements()).isEqualTo(10);
        assertThat(pagePostDtoByTitle.getNumber()).isEqualTo(0);
        assertThat(pagePostDtoByTitle.getTotalPages()).isEqualTo(2);
        assertThat(pagePostDtoByTitle.isFirst()).isTrue();
        assertThat(pagePostDtoByTitle.hasNext()).isTrue();
    }

    @Test
    @DisplayName("전체 검색")
    public void findAllTest() throws Exception {

        //given
        UserDto userDto = userService.findUserById(USER_DEFAULT_ID);
        for (int i = 0; i < 10; i++) {
            PostDto postDto = new PostDto();
            postDto.setTitle("title" + i);
            postDto.setContent("content");
            long postId = postService.savePost(postDto, userDto);
        }

        for (int i = 0; i < 10; i++) {
            PostDto postDto = new PostDto();
            postDto.setTitle("diff title" + i);
            postDto.setContent("content");
            long postId = postService.savePost(postDto, userDto);
        }

        PageRequest pageRequest = PageRequest.of(0, 5);

        //when
        Page<PostDto> pagePostDtoByTitle = postService.findAll(pageRequest);

        //then
        assertThat(pagePostDtoByTitle.getSize()).isEqualTo(5);
        assertThat(pagePostDtoByTitle.getTotalElements()).isEqualTo(20);
        assertThat(pagePostDtoByTitle.getNumber()).isEqualTo(0);
        assertThat(pagePostDtoByTitle.getTotalPages()).isEqualTo(4);
        assertThat(pagePostDtoByTitle.isFirst()).isTrue();
        assertThat(pagePostDtoByTitle.hasNext()).isTrue();
    }

}