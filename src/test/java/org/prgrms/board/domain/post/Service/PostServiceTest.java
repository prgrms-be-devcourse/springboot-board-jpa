package org.prgrms.board.domain.post.Service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.board.domain.post.domain.Post;
import org.prgrms.board.domain.post.exception.PostException;
import org.prgrms.board.domain.post.mapper.PostMapper;
import org.prgrms.board.domain.post.repository.PostRepository;
import org.prgrms.board.domain.post.request.PostCreateRequest;
import org.prgrms.board.domain.post.request.PostUpdateRequest;
import org.prgrms.board.domain.post.response.PostSearchResponse;
import org.prgrms.board.domain.user.domain.Email;
import org.prgrms.board.domain.user.domain.Name;
import org.prgrms.board.domain.user.domain.Password;
import org.prgrms.board.domain.user.domain.User;
import org.prgrms.board.domain.user.response.UserSearchResponse;
import org.prgrms.board.domain.user.service.UserService;
import org.prgrms.board.global.dto.PageDto;
import org.prgrms.board.global.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @Mock
    UserService userService;

    @InjectMocks
    PostService postService;

    @Spy
    PostMapper postMapper;

    @Test
    void 저장된_게시글_전체를_조회할수있다() {
        //given
        User user = User.builder()
                .name(Name.builder()
                        .firstName("우진")
                        .lastName("박")
                        .build())
                .password(new Password("kkkk13804943@"))
                .email(new Email("dbslzld15@naver.com"))
                .age(27)
                .build();
        Post post1 = Post.builder()
                .title("제목1")
                .content("본문1")
                .user(user)
                .build();
        Post post2 = Post.builder()
                .title("제목2")
                .content("본문2")
                .user(user)
                .build();
        Pageable pageable = Pageable.ofSize(10);
        Page<Post> postPages = new PageImpl<>(Arrays.asList(post1, post2), pageable, 2);
        when(postRepository.findAll(any(Pageable.class))).thenReturn(postPages);
        //when
        PageDto<PostSearchResponse> searchPages = postService.findAll(pageable);
        //then
        assertThat(searchPages.getContent())
                .contains(postMapper.toSearchResponse(post1), postMapper.toSearchResponse(post2));
    }

    @Nested
    class ID로_게시글_조회 {

        @Test
        void 게시글ID가_존재할경우_조회성공() {
            //given
            User user = User.builder()
                    .name(Name.builder()
                            .firstName("우진")
                            .lastName("박")
                            .build())
                    .password(new Password("kkkk13804943@"))
                    .email(new Email("dbslzld15@naver.com"))
                    .age(27)
                    .build();
            Post post = Post.builder()
                    .id(1L)
                    .title("제목")
                    .content("본문")
                    .user(user)
                    .build();
            when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
            //when
            PostSearchResponse response = postService.findById(1L);
            //then
            assertThat(response).usingRecursiveComparison()
                    .isEqualTo(postMapper.toSearchResponse(post));
        }

        @Test
        void 게시글ID가_존재하지않을경우_조회실패() {
            //given
            when(postRepository.findById(anyLong())).thenReturn(Optional.empty());
            //when
            //then
            assertThatThrownBy(() -> postService.findById(1L))
                    .isInstanceOf(PostException.class)
                    .hasMessage(ErrorCode.POST_NOT_EXIST.getMessage());
        }
    }

    @Test
    void 게시글을_저장할수있다() {
        //given
        long userId = 1L;
        User user = User.builder()
                .id(userId)
                .name(Name.builder()
                        .firstName("우진")
                        .lastName("박")
                        .build())
                .password(new Password("kkkk13804943@"))
                .email(new Email("dbslzld15@naver.com"))
                .age(27)
                .build();
        long postId = 1L;
        Post post = Post.builder()
                .id(postId)
                .title("제목")
                .content("본문")
                .user(user)
                .build();
        UserSearchResponse userSearchResponse = UserSearchResponse.builder().userId(1L).build();
        when(userService.findById(anyLong())).thenReturn(userSearchResponse);
        when(postRepository.save(any())).thenReturn(post);
        //when
        PostCreateRequest createRequest = new PostCreateRequest("제목", "본문");
        long savedId = postService.save(createRequest,userId);
        //then
        assertThat(savedId).isEqualTo(postId);
        verify(userService, times(1)).findById(anyLong());
        verify(postRepository, times(1)).save(any());
        verify(userService, times(1)).addPost(anyLong(), any());
    }

    @Nested
    class 게시글_수정 {
        @Test
        void 게시글ID가_존재할경우_수정성공() {
            //given
            User user = User.builder()
                    .name(Name.builder()
                            .firstName("우진")
                            .lastName("박")
                            .build())
                    .password(new Password("kkkk13804943@"))
                    .email(new Email("dbslzld15@naver.com"))
                    .age(27)
                    .build();
            Post post = Post.builder()
                    .id(1L)
                    .title("제목")
                    .content("본문")
                    .user(user)
                    .build();
            when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
            //when
            PostUpdateRequest updateRequest = new PostUpdateRequest("수정된 제목", "수정된 본문");
            long updatedId = postService.update(1L, updateRequest);
            //then
            assertThat(updatedId).isEqualTo(1L);
            verify(postRepository, times(1)).findById(anyLong());
        }

        @Test
        void 게시글ID가_존재하지않을경우_수정실패() {
            //given
            when(postRepository.findById(anyLong())).thenReturn(Optional.empty());
            //when
            PostUpdateRequest updateRequest = new PostUpdateRequest("수정된 제목", "수정된 본문");
            //then
            assertThatThrownBy(() -> postService.update(1L, updateRequest))
                    .isInstanceOf(PostException.class)
                    .hasMessage(ErrorCode.POST_NOT_EXIST.getMessage());
        }

    }

    @Nested
    class 게시글_삭제 {
        @Test
        void 게시글ID가_존재할경우_삭제성공() {
            //given
            User user = User.builder()
                    .name(Name.builder()
                            .firstName("우진")
                            .lastName("박")
                            .build())
                    .password(new Password("kkkk13804943@"))
                    .email(new Email("dbslzld15@naver.com"))
                    .age(27)
                    .build();
            Post post = Post.builder()
                    .id(1L)
                    .title("제목")
                    .content("본문")
                    .user(user)
                    .build();
            when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
            //when
            long updatedId = postService.delete(1L);
            //then
            assertThat(updatedId).isEqualTo(1L);
            verify(postRepository, times(1)).findById(anyLong());
            verify(postRepository, times(1)).deleteById(anyLong());
        }

        @Test
        void 게시글ID가_존재하지않을경우_삭제실패() {
            //given
            when(postRepository.findById(anyLong())).thenReturn(Optional.empty());
            //when
            //then
            assertThatThrownBy(() -> postService.delete(1L))
                    .isInstanceOf(PostException.class)
                    .hasMessage(ErrorCode.POST_NOT_EXIST.getMessage());
        }
    }

}