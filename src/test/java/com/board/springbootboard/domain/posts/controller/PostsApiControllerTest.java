package com.board.springbootboard.domain.posts.controller;


import com.board.springbootboard.domain.posts.PostsEntity;
import com.board.springbootboard.domain.posts.PostsRepository;
import com.board.springbootboard.domain.posts.dto.PostsSaveRequestDto;
import com.board.springbootboard.domain.posts.dto.PostsUpdateRequestsDto;
import org.junit.After;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }


    @Test
    @DisplayName("게시글 등록")
    public void posts_등록() throws Exception {
        // Given
        String title="title";
        String content="content";
        PostsSaveRequestDto requestDto=PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("sds1zzang")
                .build();

        String url="http://localhost:" + port +"/api/v1/posts";

        //When
        ResponseEntity<Long> responseEntity=restTemplate.postForEntity(url,requestDto,Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        log.info("responseEntity.getBody {}",responseEntity.getBody());

        List<PostsEntity>all=postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

    }


    @Test
    @DisplayName("게시글 수정")
    public void posts_수정() throws Exception {
        // Given
        PostsEntity savePosts=postsRepository.save(PostsEntity.builder()
                .title("title")
                .content("content")
                .author("sds1zzang")
                .build());

        Long updateId= savePosts.getId();
        String updateTitle="title2";
        String updateContent="content2";

        PostsUpdateRequestsDto postsUpdateRequestsDto=PostsUpdateRequestsDto.builder()
                .title(updateTitle)
                .content(updateContent)
                .build();

        String url="http://localhost:"+port+"/api/v1/posts/"+updateId;
        HttpEntity<PostsUpdateRequestsDto> requestsDtoHttpEntity=new HttpEntity<>(postsUpdateRequestsDto);

        // When
        ResponseEntity<Long>responseEntity=restTemplate.exchange(url, HttpMethod.PUT,requestsDtoHttpEntity,Long.class);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<PostsEntity>all=postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(updateTitle);
        assertThat(all.get(0).getContent()).isEqualTo(updateContent);

    }




}