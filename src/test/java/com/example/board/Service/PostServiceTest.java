package com.example.board.Service;

import com.example.board.Dto.PostDto;
import com.example.board.Dto.UserDto;
import com.example.board.Repository.PostRepository;
import com.example.board.converter.Converter;
import com.example.board.domain.Post;
import com.example.board.domain.User;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Slf4j
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;



    private UserDto userDto;

    @BeforeEach
    void createUser(){
        userDto=UserDto.builder()
                .name("박연수")
                .age(24)
                .hobby("놀기")
                .build();

    }


    @Test
    @Transactional
    void insert(){

        PostDto postDto = PostDto.builder()
                .title("test확인")
                .content("post test중입니다")
                .user(userDto)
                .build();

        Long id = postService.save(postDto);


        var post = postRepository.findById(id).get();

        assertThat(post.getTitle(),is("test확인"));
        assertThat(post.getContent(),is("post test중입니다"));
        User user=post.getUser();
        assertThat(user.getName(),is("박연수"));
    }

    @Test
    @Transactional
    void update() throws NotFoundException {

        PostDto postDto = PostDto.builder()
                .title("test확인")
                .content("post test중입니다")
                .user(userDto)
                .build();

        Long postId =postService.save(postDto);

        PostDto postDto1 = PostDto.builder()
                .title("update 확인")
                .content("update test중입니다")
                .user(
                        UserDto.builder()
                                .age(24)
                                .name("박연수")
                                .hobby("놀기")
                                .build()
                )
                .build();

        postService.update(postId, postDto1.getTitle(),postDto1.getContent());
        Post byId = postRepository.findAll().get(0);

        assertThat(postDto1.getContent(),is(byId.getContent()));
        assertThat(postDto1.getTitle(),is(byId.getTitle()));
        assertThat(userDto.getName(),is(byId.getUser().getName()));


    }
    @Test
    void findOne() throws NotFoundException {
        PostDto postDto = PostDto.builder()
                .title("test확인")
                .content("post test중입니다")
                .user(userDto)
                .build();
        Long id = postService.save(postDto);
        PostDto one = postService.findOne(id);

        assertThat(postDto.getTitle(),is(one.getTitle()));
        assertThat(postDto.getContent(),is(one.getContent()));


    }
    @Test
    void findAll(){
        PostDto postDto = PostDto.builder()
                .title("test확인")
                .content("post test중입니다")
                .user(userDto)
                .build();
        postService.save(postDto);
        PageRequest page=PageRequest.of(0,10);

        Page<PostDto> all=postService.findAll(page);

        assertThat(all.getTotalPages(),is(1));

    }


}