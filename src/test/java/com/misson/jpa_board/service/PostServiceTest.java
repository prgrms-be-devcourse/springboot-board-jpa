package com.misson.jpa_board.service;

import com.misson.jpa_board.converter.PostConverter;
import com.misson.jpa_board.domain.Hobby;
import com.misson.jpa_board.dto.PostCreateRequest;
import com.misson.jpa_board.dto.PostDto;
import com.misson.jpa_board.dto.UserDto;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostConverter postConverter;
    @Autowired
    UserService userService;

    private PostDto postDto;
    private UserDto userDto;
    private Long postNumber;

    @BeforeEach
    void save() throws NotFoundException {
        UserDto user = UserDto.builder()
                .name("choi")
                .age(28)
                .hobby(new Hobby("coding"))
                .build();
        userDto = userService.insert(user);
        log.info("userEntity.getId() : {}", userDto.getId());

        PostCreateRequest createPost = PostCreateRequest.builder()
                .title("제목은 뭐로할까?")
                .content("게시판 컨텐츠")
                .userId(userDto.getId())
                .build();
        postDto = postConverter.convertPostDtoFromPostCreateRequest(createPost);
        postNumber = postService.save(createPost);
    }

    @Test
    void findById() throws NotFoundException {
        PostDto postDto = postService.postFindById(postNumber);

        assertThat(postDto.getId(), is(postNumber));
        assertThat(postDto.getContent(), is(this.postDto.getContent()));
        assertThat(postDto.getTitle(), is(this.postDto.getTitle()));
    }

    @Test
    void postChange() throws NotFoundException {
        PostDto postDto = postService.postFindById(postNumber);
        postDto.setContent("Hello");
        postDto.setTitle("World");

        PostDto changedPost = postService.postChange(postNumber, postDto);

        assertThat(changedPost.getContent(), is("Hello"));
        assertThat(changedPost.getTitle(), is("World"));
    }

    @Test
    void findAll() {
        Page<PostDto> all = postService.findAll(PageRequest.of(0, 10));

        assertThat(all.getTotalElements(), is(1L));
        assertThat(all.stream().findFirst().orElseThrow().getTitle(), is("제목은 뭐로할까?"));
        assertThat(all.stream().findFirst().orElseThrow().getContent(), is("게시판 컨텐츠"));
    }
}
