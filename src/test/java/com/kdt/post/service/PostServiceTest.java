package com.kdt.post.service;

import com.kdt.post.dto.PostDto;
import com.kdt.post.model.Post;
import com.kdt.post.repository.PostRepository;
import com.kdt.user.dto.UserDto;
import com.kdt.user.service.UserService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Slf4j
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    Long userId;

    Long postId;

    PostDto postDto;

    @BeforeEach
    void setUp() throws NotFoundException {
        UserDto userDto = UserDto.builder()
                .name("son")
                .age(30)
                .hobby("soccer")
                .build();

        userId = userService.save(userDto);

        postDto = PostDto.builder()
                .title("test-title")
                .conent("this is a sample post")
                .build();

        postId = postService.save(userId, postDto);
    }

    @AfterEach
    void tearDown(){
        List<Post> all = postRepository.findAll();
        all.forEach((post) -> {
            try {
                postService.delete(post.getUser().getId(), post.getId());
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    @DisplayName("로그인 상태에서 게시물이 정상적으로 등록하는지 확인한다.")
    void saveTest() throws NotFoundException {
        //Given
        PostDto postDto2 = PostDto.builder()
                .title("test-title")
                .conent("this is a sample post")
                .build();

        //When
        postService.save(userId, postDto2);

        //Then
        UserDto user = userService.find(userId);
        List<PostDto> postDtosOfUser = user.getPostDtos();

        assertThat(postDtosOfUser.size(), is(2));
        assertThat(postDtosOfUser, hasItem(samePropertyValuesAs(postDto, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt")));
        assertThat(postDtosOfUser, hasItem(samePropertyValuesAs(postDto2, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt")));
        postDtosOfUser.forEach(p -> log.info(p.toString()));

        List<Post> postDtos = postRepository.findAll();
        assertThat(postDtos.size(), is(2));
    }

    @Test
    @DisplayName("로그아웃 상태에서 게시물을 등록하면 예외가 발생한다.")
    void saveTestWhenSingOut(){
        //Given
        PostDto postDto2 = PostDto.builder()
                .title("test-title")
                .conent("this is a sample post")
                .build();

        //When
        //Then
        assertThrows(NotFoundException.class, () -> postService.update(null, postDto2));

        Page<PostDto> postDtos = postService.findAll(PageRequest.of(0, 10));
        assertThat(postDtos.getTotalElements(), is(1L));
        assertThat(postDtos, hasItem(samePropertyValuesAs(postDto, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt")));
        assertThat(postDtos.toList().get(0).getId(), is(postId));
        postDtos.forEach(p -> log.info(p.toString()));
    }

    @Test
    @DisplayName("로그인 상태에서 게시물이 정상적으로 수정되는지 확인한다.")
    void updateTest() throws NotFoundException {
        //Given
        PostDto savedPost = postService.find(postId);

        //When
        //sleep for checking lastUpdatedAt
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        savedPost.setTitle("update-title");
        savedPost.setConent("this is a updated post.");
        postService.update(userId, savedPost);

        //Then
        PostDto updatedPostDto = postService.find(postId);
        assertThat(updatedPostDto, samePropertyValuesAs(savedPost, "userDto", "lastUpdatedAt"));
        log.info(updatedPostDto.toString());
    }

    @Test
    @DisplayName("로그아웃 상태에서 게시물을 수정하면 예외가 발생한다.")
    void updateTestWhenSignOut() throws NotFoundException {
        //Given
        PostDto savedPost = postService.find(postId);

        //When
        savedPost.setTitle("update-title");
        savedPost.setConent("this is a updated post.");

        //Then
        assertThrows(NotFoundException.class, () -> postService.update(null, savedPost));

        PostDto notUpdatedPostDto = postService.find(postId);
        assertThat(notUpdatedPostDto, samePropertyValuesAs(postDto, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt"));
        assertThat(notUpdatedPostDto.getId(), is(postId));
        log.info(notUpdatedPostDto.toString());
    }

    @Test
    @DisplayName("사용자가 작성하지 않은 게시물을 수정하면 예외가 발생한다.")
    void updateInvalidPostForUser() throws NotFoundException {
        //Given
        UserDto userDto2 = UserDto.builder()
                .name("son")
                .age(30)
                .hobby("soccer")
                .build();

        Long userId2 = userService.save(userDto2);

        PostDto postDto2 = PostDto.builder()
                .title("test-title")
                .conent("this is a sample post")
                .build();

        Long postId2 = postService.save(userId2, postDto2);

        //When
        PostDto savedPostOfUser2 = postService.find(postId2);
        savedPostOfUser2.setTitle("update-title");
        savedPostOfUser2.setConent("this is a updated post.");

        //Then
        assertThrows(NotFoundException.class, () -> postService.update(userId, savedPostOfUser2));

        PostDto notUpdatedPostDto = postService.find(postId2);
        assertThat(notUpdatedPostDto, samePropertyValuesAs(postDto2, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt"));
        assertThat(notUpdatedPostDto.getId(), is(postId2));
        log.info(notUpdatedPostDto.toString());

        assertThat(userService.find(userId).getPostDtos().get(0), samePropertyValuesAs(postDto, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt"));
        assertThat(userService.find(userId2).getPostDtos().get(0), samePropertyValuesAs(postDto2, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt"));
        log.info(userService.find(userId).getPostDtos().get(0).toString());
        log.info(userService.find(userId2).getPostDtos().get(0).toString());
    }

    @Test
    @DisplayName("로그인 상태에서 게시물을 정삭적으로 삭제하는지 확인한다.")
    void deleteTest() throws NotFoundException {
        //Given
        //When
        postService.delete(userId, postId);

        //Then
        assertThrows(NotFoundException.class, () -> postService.find(postId));
        assertThat(postRepository.findAll().size(), is(0));
        assertThat(userService.find(userId).getPostDtos().size(), is(0));
    }

    @Test
    @DisplayName("로그아웃 상태에서 게시물을 삭제하면 예외가 발생한다.")
    void deleteTestWhenSignOut() throws NotFoundException {
        //Given
        //When
        assertThrows(NotFoundException.class, () -> postService.delete(null, postId));

        //Then
        assertThat(postService.find(postId), samePropertyValuesAs(postDto, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt"));
        assertThat(postRepository.findAll().size(), is(1));
        assertThat(userService.find(userId).getPostDtos().size(), is(1));
        assertThat(userService.find(userId).getPostDtos().get(0), samePropertyValuesAs(postDto, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt"));
    }

    @Test
    @DisplayName("사용자가 작성하지 않은 게시물을 삭제하면 예외가 발생한다.")
    void deleteInvalidPostForUserTest() throws NotFoundException {
        //Given
        UserDto userDto2 = UserDto.builder()
                .name("son")
                .age(30)
                .hobby("soccer")
                .build();

        Long userId2 = userService.save(userDto2);

        PostDto postDto2 = PostDto.builder()
                .title("test-title")
                .conent("this is a sample post")
                .build();

        Long postId2 = postService.save(userId2, postDto2);

        //When
        //Then
        assertThrows(NotFoundException.class, () -> postService.delete(userId, postId2));

        Page<PostDto> posts = postService.findAll(PageRequest.of(0, 10));
        assertThat(posts.getTotalElements(), is(2L));
        assertThat(posts, hasItem(samePropertyValuesAs(postDto, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt")));
        assertThat(posts, hasItem(samePropertyValuesAs(postDto2, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt")));

        assertThat(userService.find(userId).getPostDtos().size(), is(1));
        assertThat(userService.find(userId).getPostDtos().get(0), samePropertyValuesAs(postDto, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt"));

        assertThat(userService.find(userId2).getPostDtos().size(), is(1));
        assertThat(userService.find(userId2).getPostDtos().get(0), samePropertyValuesAs(postDto2, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt"));
    }

    @Test
    @DisplayName("게시물이 정상적으로 조회되는지 확인한다.")
    void findTest() throws NotFoundException {
        //Given
        //When
        PostDto post = postService.find(postId);

        //Then
        assertThat(post, samePropertyValuesAs(postDto, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt"));
        assertThat(post.getId(), is(postId));
        log.info(post.toString());

        assertThat(userService.find(userId).getPostDtos().get(0), samePropertyValuesAs(post, "userDto"));
    }

    @Test
    @DisplayName("모든 게시물이 정상적으로 조회되는지 확인한다.")
    void findAllTest() throws NotFoundException {
        //Given
        PostDto postDto2 = PostDto.builder()
                .title("test-title")
                .conent("this is a sample post")
                .build();
        postService.save(userId, postDto2);

        //When
        Page<PostDto> posts = postService.findAll(PageRequest.of(0, 10));

        //Then
        assertThat(posts.getTotalElements(), is(2L));
        assertThat(posts, hasItem(samePropertyValuesAs(postDto, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt")));
        assertThat(posts, hasItem(samePropertyValuesAs(postDto2, "id", "userDto", "createdAt", "createdBy", "lastUpdatedAt")));
        posts.forEach(p -> log.info(p.toString()));
    }
}