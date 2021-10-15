package com.kdt.Board.service;

import com.kdt.Board.dto.PostRequest;
import com.kdt.Board.dto.PostResponse;
import com.kdt.Board.dto.UserResponse;
import com.kdt.Board.entity.Post;
import com.kdt.Board.entity.User;
import com.kdt.Board.repository.PostRepository;
import com.kdt.Board.utils.ConversionDtoEntity;
import javassist.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.security.sasl.AuthenticationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
class PostServiceTest {

    @Autowired private PostService postService;
    @Autowired private PostRepository postRepository;
    @Autowired private ConversionDtoEntity conversion;
    private User user;
    private Post savedPost;

    @BeforeEach
    void init() {
        user = User.builder()
                .name("유저1")
                .hobby("TV")
                .age(25)
                .build();
        Post post = Post.builder()
                .title("제목1")
                .content("내용1")
                .user(user)
                .build();
        // 연관관계의 주인이 Post이므로 Post에서 저장. Post저장시 User도 저장이 됨.
        savedPost = postRepository.save(post);
        user = savedPost.getUser(); //테스트에서 사용할 유저 세팅
    }

    @AfterEach
    void clear() {
        postRepository.deleteAll();
    }

    @Test
    void 게시글_단건_조회() throws NotFoundException {
        //given
        //when
        final PostResponse post = postService.getPost(savedPost.getId());
        final UserResponse userResponse = conversion.toUserResponse(user);

        //then
        Assertions.assertThat(post.getTitle()).isEqualTo(savedPost.getTitle());
        Assertions.assertThat(post.getContent()).isEqualTo(savedPost.getContent());
        assertThat(post.getUserResponse(), is(samePropertyValuesAs(userResponse)));
        Assertions.assertThat(post.getCreatedAt()).isEqualTo(savedPost.getCreatedAt());
        Assertions.assertThat(post.getModifiedAt()).isEqualTo(savedPost.getModifiedAt());
    }
    
    @Test
    void 게시글_다건_조회() {
        //given
        final Post post2 = Post.builder()
                .title("제목2")
                .content("내용2")
                .user(user)
                .build();
        final PageRequest pageRequest = PageRequest.of(0, 10);
        final Page<PostResponse> posts = postService.getPosts(pageRequest);
        postService.writePost(user.getId(), new PostRequest(post2.getTitle(), post2.getContent()));

        //when
        final Page<PostResponse> posts2 = postService.getPosts(pageRequest);

        //then
        Assertions.assertThat(posts.get().count()).isEqualTo(1L);
        Assertions.assertThat(posts2.get().count()).isEqualTo(2L);
        Assertions.assertThat(posts2.getSize()).isEqualTo(pageRequest.getPageSize());
        Assertions.assertThat(posts2.getNumber()).isEqualTo(pageRequest.getPageNumber());
    }
    
    @Test
    void 게시글_작성() throws NotFoundException {
        //given
        final PostRequest postRequest = new PostRequest("제목2", "내용2");

        //when
        final Long postId = postService.writePost(user.getId(), postRequest);

        //then
        final PostResponse post = postService.getPost(postId);
        Assertions.assertThat(post.getTitle()).isEqualTo(postRequest.getTitle());
        Assertions.assertThat(post.getContent()).isEqualTo(postRequest.getContent());
    }
    
    @Test
    void 게시글_수정() throws AuthenticationException, NotFoundException {
        //given
        final PostRequest postRequest = new PostRequest("수정제목1", "수정내용1");
        //when
        final Long postId = postService.editPost(user.getId(), savedPost.getId(), postRequest);

        //then
        final PostResponse post = postService.getPost(postId);
        Assertions.assertThat(post.getTitle()).isEqualTo(postRequest.getTitle());
        Assertions.assertThat(post.getContent()).isEqualTo(postRequest.getContent());
        Assertions.assertThat(post.getCreatedAt()).isBefore(post.getModifiedAt());
    }
}