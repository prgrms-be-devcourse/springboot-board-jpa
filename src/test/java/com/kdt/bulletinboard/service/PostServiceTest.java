package com.kdt.bulletinboard.service;

import com.kdt.bulletinboard.dto.PostDto;
import com.kdt.bulletinboard.dto.UserDto;
import com.kdt.bulletinboard.entity.Hobby;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class PostServiceTest {
    @Autowired
    private PostService postService;
    private Long postId;

    @BeforeEach
    void setUp() {
        UserDto userDto = UserDto.builder()
                .name("eonju")
                .hobby(Hobby.CYCLING)
                .build();

        PostDto postDto = PostDto.builder()
                .title("1st Post")
                .content("This is first Post")
                .userDto(userDto)
                .build();

        postId = postService.save(postDto);
    }

    @Test
    void findOneTest() throws NotFoundException {
        PostDto foundPostDto = postService.findOnePost(postId);
        assertThat(postId).isEqualTo(foundPostDto.getId());
    }

    @Test
    void findAllTest() {
        PageRequest page = PageRequest.of(0, 10);
        Page<PostDto> posts = postService.findAllPost(page);
        assertThat(posts.getTotalElements()).isEqualTo(1);
    }

    @Test
    void updatePost() throws NotFoundException {
        PostDto newPostDto = PostDto.builder()
                .id(postId)
                .title("updated post")
                .content("this is updated post. ")
                .build();
        Long updatedPostId = postService.updatePost(newPostDto.getId(), newPostDto);
        assertThat(updatedPostId).isEqualTo(postId);
    }
}
