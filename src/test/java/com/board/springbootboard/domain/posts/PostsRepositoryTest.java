package com.board.springbootboard.domain.posts;


import org.junit.After;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 저장 테스트")
    public void 게시글저장테스트() {
        //Given
        String title="테스트 게시글";
        String content="테스트 본문";

        postsRepository.save(PostsEntity.builder()
                .title(title)
                .content(content)
                .author("sds1zzang")
                .build());

        //When
        List<PostsEntity> postsList=postsRepository.findAll();

        //Then
        PostsEntity posts=postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }

    @Test
    @DisplayName("BaseEntity테스트")
    public void BaseEntity테스트() {
        // Given
        LocalDateTime now=LocalDateTime.of(2020,11,1,0,0,0);

        postsRepository.save(PostsEntity.builder()
                        .title("title")
                        .content("content")
                        .author("sds1zzang")
                        .build());

        // When
        List<PostsEntity> postsList=postsRepository.findAll();

        // Then
        PostsEntity posts=postsList.get(0);
        log.info("createDate= {}, modifiedDate={}",posts.getCreatedDate(),posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);

    }



}