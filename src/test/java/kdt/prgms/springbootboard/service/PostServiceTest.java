package kdt.prgms.springbootboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

import java.util.stream.Collectors;
import java.util.stream.LongStream;
import kdt.prgms.springbootboard.converter.PostConverter;
import kdt.prgms.springbootboard.domain.Post;
import kdt.prgms.springbootboard.domain.User;
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
}
