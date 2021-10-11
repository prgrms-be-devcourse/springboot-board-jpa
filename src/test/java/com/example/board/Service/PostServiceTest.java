package com.example.board.Service;

import com.example.board.Dto.PostRequestDto;
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


    @Test
    void insert(){

        PostRequestDto postRequestDto = PostRequestDto.builder()
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

        Post result = postService.save(postRequestDto);

        assertThat(result.getTitle(),is("test확인"));
    }

    @Test
    void update() throws NotFoundException {

        PostRequestDto postRequestDto = PostRequestDto.builder()
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

        Post result = postService.save(postRequestDto);


        PostRequestDto postRequestDto1 = PostRequestDto.builder()
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

       postService.update(result.getPostId(), postRequestDto1);
        Post byId = postRepository.findById(result.getPostId()).get();



        assertThat(postRequestDto1.getContent(),is(byId.getContent()));
        assertThat(postRequestDto1.getTitle(),is(byId.getTitle()));


    }
    @Test
    void findOne() throws NotFoundException {
        PostRequestDto postRequestDto = PostRequestDto.builder()
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
        Post result = postService.save(postRequestDto);
        PostRequestDto one = postService.findOne(result.getPostId());

        assertThat(postRequestDto.getTitle(),is(one.getTitle()));
        assertThat(postRequestDto.getContent(),is(one.getContent()));


    }
    @Test
    void findAll(){
        PostRequestDto postRequestDto = PostRequestDto.builder()
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
        Post result = postService.save(postRequestDto);



        PageRequest page=PageRequest.of(0,10);

        Page<PostRequestDto> all=postService.findAll(page);

        assertThat(all.getTotalPages(),is(1));

    }
//    @AfterEach
//    void tearDown() {
//        postRepository.deleteAll();
//    }

}