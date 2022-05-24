package com.prgrms.boardapp.service;

import com.prgrms.boardapp.dto.PostDto;
import com.prgrms.boardapp.dto.UserDto;
import com.prgrms.boardapp.model.Post;
import com.prgrms.boardapp.model.User;
import com.prgrms.boardapp.repository.PostRepository;
import com.prgrms.boardapp.repository.UserRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.prgrms.boardapp.common.PostCreateUtil.createPost;
import static com.prgrms.boardapp.common.UserCreateUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Converter converter;

    User user = createUser();
    Post post = createPost();

    @BeforeEach
    void init() {
        userRepository.save(user);
        postRepository.save(post);
        post.changeUser(user);
    }

    @AfterEach
    void destroy() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @Description("init data가 정상적으로 저장되었는지 확인한다.")
    void testInit() {
        assertThat(post.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @Description("Post 정보를 변경한다.")
    void testUpdate() {
        UserDto userDto = converter.convertUserDto(user);

        String updateTitle = "update title";
        String updateContent = "update content";

        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .userDto(userDto)
                .title(updateTitle)
                .content(updateContent)
                .build();

        postService.update(postDto);
        Post findPost = postRepository.findById(post.getId()).get();
        assertAll(
                () -> assertThat(findPost.getTitle()).isEqualTo(updateTitle),
                () -> assertThat(findPost.getContent()).isEqualTo(updateContent)
        );
    }
}