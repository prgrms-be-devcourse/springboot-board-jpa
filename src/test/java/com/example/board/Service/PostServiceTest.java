package com.example.board.Service;

import com.example.board.Dto.PostDto;
import com.example.board.Dto.UserDto;
import com.example.board.Repository.PostRepository;
import com.example.board.domain.Post;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Slf4j
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;


    @Test
    void insert(){

        PostDto postDto = PostDto.builder()
                .title("test확인")
                .content("post test중입니다")
                .user(
                        UserDto.builder()
                                .age(24)
                                .name("박연수")
                                .hobby("놀기")
                                .build()
                )
                .build();

        Post result = postService.save(postDto);

        assertThat(result.getTitle(),is("test확인"));
    }

    @Test
    void update() throws NotFoundException {

        PostDto postDto = PostDto.builder()
                .title("test확인")
                .content("post test중입니다")
                .user(
                        UserDto.builder()
                                .age(24)
                                .name("박연수")
                                .hobby("놀기")
                                .build()
                )
                .build();

        Post result = postService.save(postDto);


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

       postService.update(result.getPostId(), postDto1);
        Post byId = postRepository.findById(result.getPostId()).get();



        assertThat(postDto1.getContent(),is(byId.getContent()));
        assertThat(postDto1.getTitle(),is(byId.getTitle()));


    }
    @Test
    void findOne() throws NotFoundException {
        PostDto postDto = PostDto.builder()
                .title("test확인")
                .content("post test중입니다")
                .user(
                        UserDto.builder()
                                .age(24)
                                .name("박연수")
                                .hobby("놀기")
                                .build()
                )
                .build();
        Post result = postService.save(postDto);
        PostDto one = postService.findOne(result.getPostId());

        assertThat(postDto.getTitle(),is(one.getTitle()));
        assertThat(postDto.getContent(),is(one.getContent()));


    }
    @Test
    void findAll(){
        PostDto postDto = PostDto.builder()
                .title("test확인")
                .content("post test중입니다")
                .user(
                        UserDto.builder()
                                .age(24)
                                .name("박연수")
                                .hobby("놀기")
                                .build()
                )
                .build();
        Post result = postService.save(postDto);



        PageRequest page=PageRequest.of(0,10);

        Page<PostDto> all=postService.findAll(page);

        assertThat(all.getTotalPages(),is(1));

    }
    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

}