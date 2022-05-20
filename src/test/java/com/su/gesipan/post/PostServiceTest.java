package com.su.gesipan.post;

import com.su.gesipan.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static com.su.gesipan.post.PostDtoMapper.toPostResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    // given : 주어진 값 + Mock 객체에서 기대되는 행위
    // when  : 테스트할 함수
    // then  : 예상되는 결과

    @InjectMocks
    PostService postService;
    @Mock
    PostRepository postRepository;

    @Mock
    Post post;
    @Mock
    User user;

    @Nested
    class findAll {

        @BeforeEach
        void USER_AND_POST_MOCK_SET() {
            doReturn(1L).when(user).getId();
            doReturn(1L).when(post).getId();
            doReturn("제목").when(post).getTitle();
            doReturn("본문").when(post).getContent();
            doReturn(user).when(post).getUser();
        }

        @Test
        void 행위검증() {
            // given
            var posts = List.of(post);
            var pageable = PageRequest.of(0, 10);
            var page = new PageImpl<>(posts, pageable, 1L);
            given(postRepository.findAll(any(PageRequest.class))).willReturn(page);

            // when
            postService.findAll(pageable);

            // then
            then(postRepository).should(times(1)).findAll(pageable);
        }

        @Test
        void 성공_알맞은_타입으로_잘_반환_됨() {
            // given
            var posts = List.of(post);
            var pageable = PageRequest.of(0, 10);
            var page = new PageImpl<>(posts, pageable, 1L);
            given(postRepository.findAll(any(PageRequest.class))).willReturn(page);

            // when
            var pageResult = postService.findAll(pageable);
            var pages = pageResult.getContent();

            // then
            assertAll(
                    () -> assertThat(pageResult).isInstanceOf(Page.class),
                    () -> assertThat(pages).contains(toPostResult(post)),
                    () -> assertThat(pages.get(0)).isInstanceOf(PostDto.Result.class)
            );
        }
    }

    @Nested
    class findById {
        @Test
        void 행위검증() {
            // given
            doReturn(1L).when(user).getId();
            doReturn(1L).when(post).getId();
            doReturn(user).when(post).getUser();

            given(postRepository.findById(any(Long.class))).willReturn(Optional.of(post));

            // when
            postService.findById(post.getId());
            // then
            then(postRepository).should(times(1)).findById(post.getId());
        }

        @Test
        void 실패_아이디_없으면_못찾음() {
            // given
            given(postRepository.findById(any(Long.class))).willReturn(Optional.empty());
            // when
            assertThatThrownBy(() -> postService.findById(2L))
                    // then
                    .isInstanceOf(PostNotFoundException.class);
        }
    }

    @Nested
    class editPost{
        @Test
        void 행위검증() {
            // given
            var updateDto = PostDto.Update.of("수정제목", "수정본문");
            doReturn(1L).when(post).getId();
            given(postRepository.findById(any(Long.class))).willReturn(Optional.of(post));

            // when
            postService.editPost(post.getId(), updateDto);

            // then
            then(postRepository).should(times(1)).findById(post.getId());
            then(post).should(times(1)).editTitle(anyString());
            then(post).should(times(1)).editContent(anyString());
        }

        @Test
        void 실패_아이디_없으면_수정_못함(){
            // given
            var updateDto = PostDto.Update.of("수정제목", "수정본문");
            given(postRepository.findById(any(Long.class))).willReturn(Optional.empty());

            // when
            assertThatThrownBy(() -> postService.editPost(2L, updateDto))
                    // then
                    .isInstanceOf(PostNotFoundException.class);
        }

    }

}
