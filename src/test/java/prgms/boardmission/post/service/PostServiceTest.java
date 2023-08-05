package prgms.boardmission.post.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import prgms.boardmission.member.dto.MemberDto;
import prgms.boardmission.post.dto.PostDto;
import prgms.boardmission.post.dto.PostUpdateDto;

import java.util.NoSuchElementException;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    private PostService postService;

    private MemberDto memberDto;
    private PostDto postDto;

    private PageRequest pageable = PageRequest.of(0, 10);

    @BeforeEach
    void setUp() {
        long id = 1L;
        String name = "sehan";
        int age = 20;
        String hobby = "hobby";

        memberDto = new MemberDto(name, age, hobby);

        Long postId = 1L;
        String title = "title";
        String content = "content";

        postDto = new PostDto(title, content, memberDto);

        postService.save(postDto);
    }

    @Test
    void save() {
        //Given
        Long postId = 2L;
        String title = "another title";
        String content = "another content";

        PostDto newPostDto = new PostDto(title, content, memberDto);
        postService.save(newPostDto);

        //When
        Page<PostDto> allPosts = postService.findAll(pageable);

        //Then
        Assertions.assertEquals(2, allPosts.getTotalElements());
    }

    @Nested
    class findById {
        @Test
        void findById_success() {
            //When
            PostDto post = postService.findById(1L);

            //Then
            Assertions.assertEquals("title", post.title());
        }

        @Test
        void findById_fail() {
            Assertions.assertThrows(NoSuchElementException.class, () -> postService.findById(2L));
        }
    }

    @Test
    void findAll() {
        //When
        Page<PostDto> allPosts = postService.findAll(pageable);

        //Then
        Assertions.assertEquals(1, allPosts.getTotalPages());
    }

    @Test
    void update() {
        //Given
        String editContent = "edit content";

        PostUpdateDto postUpdateDto = new PostUpdateDto(editContent);
        //When
        postService.update(1L, postUpdateDto);
        PostDto editPost = postService.findById(1L);

        //Then
        Assertions.assertEquals(editContent, editPost.content());
    }
}
