package kdt.prgms.springbootboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import kdt.prgms.springbootboard.converter.PostConverter;
import kdt.prgms.springbootboard.domain.Post;
import kdt.prgms.springbootboard.domain.User;
import kdt.prgms.springbootboard.dto.PostDetailResponseDto;
import kdt.prgms.springbootboard.dto.PostSaveRequestDto;
import kdt.prgms.springbootboard.dto.SimpleUserDto;
import kdt.prgms.springbootboard.repository.PostRepository;
import kdt.prgms.springbootboard.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;


@ExtendWith(MockitoExtension.class)
@Slf4j
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostConverter postConvertor;

    @Test
    @DisplayName("게시글 생성 테스트")
    void savePostTest() {
        //given
        var user = new User("tester", 20);
        var post = new Post("title", "content", user);
        var simpleUserDto = mock(SimpleUserDto.class);
        var postSaveRequestDto = mock(PostSaveRequestDto.class);

        given(postSaveRequestDto.getSimpleUserDto()).willReturn(simpleUserDto);
        given(simpleUserDto.getName()).willReturn("tester");
        given(userRepository.findByName(any(String.class))).willReturn(Optional.of(user));
        given(postConvertor.convertPost(any(PostSaveRequestDto.class), any(User.class))).willReturn(post);
        given(postRepository.save(any(Post.class))).willReturn(post);
        given(postConvertor.convertPostDetailDto(any(Post.class))).willReturn(mock(PostDetailResponseDto.class));

        //when
        postService.save(postSaveRequestDto);

        //then
        verify(userRepository, times(1)).findByName(any(String.class));
        verify(postConvertor, times(1)).convertPost(any(PostSaveRequestDto.class), any(User.class));
        verify(postRepository, times(1)).save(any(Post.class));
        verify(postConvertor, times(1)).convertPostDetailDto(any(Post.class));
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void updatePostTest() {
        //given
        var post = new Post("title", "content", mock(User.class));
        var postSaveRequestDto = mock(PostSaveRequestDto.class);

        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(postConvertor.convertPostDetailDto(any(Post.class))).willReturn(mock(PostDetailResponseDto.class));

        //when
        postService.update(anyLong(), postSaveRequestDto);

        //then
        verify(postRepository, times(1)).findById(anyLong());
        verify(postConvertor, times(1)).convertPostDetailDto(any(Post.class));
    }

    @Test
    @DisplayName("전체 게시글 조회 테스트")
    void findAllPostTest() {
        //given
        var pageRequest = PageRequest.of(0, 10);
        var posts = LongStream.range(0, 20)
            .mapToObj(i -> new Post("title#" + i, "content#" + i, mock(User.class)))
            .collect(Collectors.toList());
        given(postRepository.findAll(pageRequest)).willReturn(new PageImpl<>(posts));

        //when
        var result = postService.findAll(pageRequest);

        //then
        assertThat(result.getTotalElements()).isEqualTo(20L);
        verify(postRepository, times(1)).findAll(pageRequest);
    }

    @Test
    @DisplayName("게시글 단건 조회 테스트")
    void findOnePostTest() {
        //given
        var post = new Post("title", "content", mock(User.class));
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(postConvertor.convertPostDetailDto(any(Post.class))).willReturn(mock(PostDetailResponseDto.class));

        //when
        postService.findOne(anyLong());

        //then
        verify(postRepository, times(1)).findById(anyLong());
        verify(postConvertor, times(1)).convertPostDetailDto(any(Post.class));
    }
}
