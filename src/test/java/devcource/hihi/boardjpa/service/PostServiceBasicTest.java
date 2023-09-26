package devcource.hihi.boardjpa.service;

import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.domain.User;
import devcource.hihi.boardjpa.dto.post.CreatePostRequestDto;
import devcource.hihi.boardjpa.dto.post.ResponsePostDto;
import devcource.hihi.boardjpa.repository.PostRepository;
import devcource.hihi.boardjpa.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PostServiceBasicTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    private User user;

    private Post post;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("테스트용 고객")
                .age(45)
                .hobby("숨쉬기")
                .build();

        ReflectionTestUtils.setField(
                user,
                "id",
                1L
        );

        post = Post.builder()
                .title("테스트용")
                .content("테스트 컨텐츠")
                .user(user)
                .build();

    }

    @Test
    @DisplayName("post를 저장한다.")
    public void saveTest(){
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .userId(user.getId())
                .build();

        given(postRepository.save(any(Post.class))).willReturn(post);

        //when
        ResponsePostDto postDto = postService.createPost(createPostRequestDto);

        //then
        assertEquals(postDto.userId(),user.getId());
    }

}
