package com.prgrms.springbootboardjpa.post.service;

import com.prgrms.springbootboardjpa.post.dto.PostConverter;
import com.prgrms.springbootboardjpa.post.dto.PostDto;
import com.prgrms.springbootboardjpa.post.dto.PostResponse;
import com.prgrms.springbootboardjpa.post.entity.Post;
import com.prgrms.springbootboardjpa.post.repository.PostRepository;
import com.prgrms.springbootboardjpa.user.dto.UserDto;
import com.prgrms.springbootboardjpa.user.dto.UserResponse;
import com.prgrms.springbootboardjpa.user.entity.User;
import com.prgrms.springbootboardjpa.user.repository.UserRepository;
import com.prgrms.springbootboardjpa.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostConverter postConverter;

    User user;
    UserDto userDto;
    UserResponse userResponse;
    Post post;
    PostDto postDto;
    PostResponse postResponse;

    @BeforeEach
    void setUp(){
        userDto = UserDto.builder()
                .nickName("Nickname")
                .age(20)
                .hobby("Sleep")
                .firstName("Ella")
                .lastName("Ma")
                .password("Password123")
                .email("test@gmail.com")
                .build();

        userResponse = userService.save(userDto);

        user = userRepository.findById(userResponse.getId()).get();

        postDto = PostDto.builder()
                .title("First Title")
                .content("First content")
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();

        postResponse = postService.save(postDto);

        post = postRepository.findById(postResponse.getId()).get();
    }

    @AfterEach
    void clearUp(){
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void save() {
        //Given
        PostDto givenPostDto = PostDto.builder()
                    .title("New Title")
                    .content("New Content")
                    .email(userDto.getEmail())
                    .password(userDto.getPassword())
                    .build();
        PostResponse expectPostResponse = postConverter.convertToPostResponse(
                                            postConverter.convertToPost(givenPostDto,user)
                                            );

        //When
        PostResponse saveResult = postService.save(givenPostDto);

        //Then
        assertThat(saveResult).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectPostResponse);
    }

    @Test
    void findAllWithoutPage() {
        //Given
        PostDto givenPostDto = PostDto.builder()
                .title("New Title")
                .content("New Content")
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
        PostResponse givenPostResponse = postService.save(givenPostDto);

        List<PostResponse> postResponseList = new ArrayList<>();
        postResponseList.add(postResponse);
        postResponseList.add(givenPostResponse);

        //When
        Page<PostResponse> postResponsePage = postService.findAll(Pageable.unpaged());

        //Then
        assertThat(postResponsePage.stream().toList()).usingRecursiveFieldByFieldElementComparator().isEqualTo(postResponseList);


    }

    @Test
    void findAllWithPage(){
        //Given
        PostDto givenPostDto = PostDto.builder()
                .title("New Title")
                .content("New Content")
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
        PostResponse givenPostResponse = postService.save(givenPostDto);

        List<PostResponse> postResponseList = new ArrayList<>();
        postResponseList.add(givenPostResponse);

        //When
        Page<PostResponse> postResponsePage = postService.findAll(Pageable.ofSize(1).withPage(1));

        //Then
        assertThat(postResponsePage.stream().toList()).usingRecursiveFieldByFieldElementComparator().isEqualTo(postResponseList);

    }

    @Test
    void update() {
        //Given
        postDto.setId(post.getId());
        postDto.setTitle("Update Title");
        postDto.setContent("Update Content");

        PostResponse expectResponse = PostResponse.builder()
                                        .id(postDto.getId())
                                        .title(postDto.getTitle())
                                        .content(postDto.getContent())
                                        .userNickName(post.getUser().getNickName())
                                        .build();

        //When
        PostResponse updatedResult = postService.update(postDto);

        //Then
        assertThat(updatedResult).usingRecursiveComparison().isEqualTo(expectResponse);
    }
}