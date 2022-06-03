package org.programmers.springboardjpa.post.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springboardjpa.domain.post.dto.PostRequest;
import org.programmers.springboardjpa.domain.post.dto.PostResponse;
import org.programmers.springboardjpa.domain.post.repository.PostRepository;
import org.programmers.springboardjpa.domain.post.service.PostConverter;
import org.programmers.springboardjpa.domain.post.service.PostService;
import org.programmers.springboardjpa.domain.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostDefaultServiceStubTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostConverter postConverter;

    @Autowired
    PostService postService;

    PostResponse.PostResponseDto savedPost;

    PostRequest.PostCreateRequestDto postCreateRequest;

    @BeforeEach
    void setUp() {
        postCreateRequest = new PostRequest.PostCreateRequestDto(
                "아이유는", "어느집 아이유", new UserDto.UserRequest(
                "이상혁", 15,  "LOL"));
        savedPost = postService.savePost(postCreateRequest);
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("id값이 자동 생성 전략에 따라 할당 되어 null이 아님을 확인 할 수 있다.")
    void savePost() {
        //then
        assertThat(savedPost.id()).isNotNull();
    }

    @Test
    @DisplayName("저장된 게시글의 아이디 값으로 찾아온 게시글의 아이디 값을 비교하여 동일한 게시글을 가져 올 수 있다.")
    void findPost() {
        //given
        var foundPost = postService.getPost(savedPost.id());

        //then
        assertThat(foundPost.id()).isEqualTo(savedPost.id());
    }

    @Test
    @DisplayName("PageRequst의 page와 size를 이용하여 해당하는 postRespone의 list를 반환할 수 있다.")
    void getAllPost() {
        //given
        var post = postService.getPost(savedPost.id());
        List<PostResponse.PostResponseDto> postResponseList = new ArrayList<>();
        postResponseList.add(post);

        //when
        List<PostResponse.PostResponseDto> postList = postService.getPostList(PageRequest.of(0, 1));

        //then
        assertThat(postList).hasSize(1)
                .usingRecursiveFieldByFieldElementComparator().isEqualTo(postResponseList);
    }

    @Test
    @DisplayName("동일한 id를 이용하여 update된 title과 content를 확인하여 update를 검증할 수 있다.")
    void updatePost() {
        //given
        var requestDto = new PostRequest.PostUpdateRequestDto("아이유가", "뭐하는 아이유?");

        //when
        postService.updatePost(savedPost.id(), requestDto);
        var updatedPost = postService.getPost(savedPost.id());

        //then
        assertThat(updatedPost.id()).isEqualTo(savedPost.id());
        assertThat(updatedPost.title()).isEqualTo(requestDto.title());
        assertThat(updatedPost.content()).isEqualTo(requestDto.content());
    }
}
