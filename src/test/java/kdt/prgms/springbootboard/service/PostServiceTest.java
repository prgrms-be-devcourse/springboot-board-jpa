package kdt.prgms.springbootboard.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;

import java.util.stream.Collectors;
import java.util.stream.LongStream;
import kdt.prgms.springbootboard.converter.PostConverter;
import kdt.prgms.springbootboard.domain.Post;
import kdt.prgms.springbootboard.domain.User;
import kdt.prgms.springbootboard.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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
    private PostConverter postConvertor;

    private User user;

    @BeforeEach
    void setUp() {
        var user = new User("tester#1", 1);
    }

    @Test
    void 전체_게시글_조회_성공() {
        //given
        var pageRequest = PageRequest.of(0, 10);
        var posts = LongStream.range(0, 20)
            .mapToObj(i -> new Post("title#" + i, "content#" + i, user))
            .collect(Collectors.toList());
        given(postRepository.findAll(pageRequest)).willReturn(new PageImpl<>(posts));

        //when
        var result = postService.findAll(pageRequest);

        //then
        assertThat(result.getTotalElements(), is(20L));
        verify(postRepository, times(1)).findAll(pageRequest);
    }


}
