package com.su.gesipan.user;

import com.su.gesipan.post.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    // given : 주어진 값 + Mock 객체에서 기대되는 행위
    // when  : 테스트할 함수
    // then  : 예상되는 결과

    @InjectMocks
    UserService userService;
    @Mock
    PostRepository postRepository;
    @Mock
    UserRepository userRepository;

    @Mock
    Post post;
    @Mock
    User user;

    @Nested
    class createPost {
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
            var createDto = PostDto.Create.of(user.getId(), "제목", "본문");
            given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
            given(postRepository.findByUserId(any(Long.class))).willReturn(Optional.of(post));

            // when
            userService.createPost(createDto);

            // then
            then(userRepository).should(times(1)).findById(user.getId());
            then(postRepository).should(times(1)).findByUserId(user.getId());
        }

        @Test
        void 성공_알맞은_타입_DTO_로_잘_반환_됨() {
            // given
            var createDto = PostDto.Create.of(user.getId(), "제목", "본문");
            given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
            given(postRepository.findByUserId(any(Long.class))).willReturn(Optional.of(post));

            // when
            var result = userService.createPost(createDto);

            // then
            assertThat(result).isInstanceOf(PostDto.Result.class);
        }
    }

    @Nested
    class createUser {
        @Test
        void 행위검증() {
            // given
            var createDto = UserDto.Create.of("su", 24L, "게임");
            given(userRepository.save(any(User.class))).willReturn(user);

            // when
            userService.createUser(createDto);

            // then
            then(userRepository).should(times(1)).save(any(User.class));
        }

        @Test
        void 성공_알맞은_타입_DTO_로_잘_반환_됨() {
            // given
            var createDto = UserDto.Create.of("su", 24L, "게임");
            given(userRepository.save(any(User.class))).willReturn(user);

            // when
            var result = userService.createUser(createDto);

            // then
            assertThat(result).isInstanceOf(UserDto.Result.class);
        }
    }

}
