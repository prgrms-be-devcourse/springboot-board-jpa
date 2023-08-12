package org.prgms.boardservice.domain.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.boardservice.domain.post.vo.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.prgms.boardservice.util.ErrorMessage.NOT_FOUND_POST;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;

    private final Post post = new Post(new Title("title"), new Content("content"), 1L);

    @BeforeEach
    void setUp() {
        postService.create(post);
    }

    @Test
    @DisplayName("게시글이 성공적으로 생성된다.")
    void success_Create_Post() {
        Long id =  postService.create(new Post(new Title("title"), new Content("content"), 1L));

        Post get = postService.getById(id);

        assertThat(get).usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt")
                .isEqualTo(post);
    }

    @Test
    @DisplayName("게시글이 성공적으로 수정된다.")
    void success_Update_Post() {
        PostUpdate postUpdate = new PostUpdate(post.getId(), "new-title", "new-content");

        Long id = postService.update(postUpdate);

        Post updated = postService.getById(id);

        assertThat(updated).usingRecursiveComparison()
                .comparingOnlyFields("id", "title", "content")
                .isEqualTo(postUpdate);
    }

    @Test
    @DisplayName("존재하지 않는 게시글은 수정에 실패한다.")
    void fail_Update_Post() {
        PostUpdate postUpdate = new PostUpdate(Long.MAX_VALUE, "title", "content");

        assertThatThrownBy(() -> postService.update(postUpdate))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_FOUND_POST.getMessage());
    }

    @Test
    @DisplayName("게시글을 id로 조회할 수 있다.")
    void success_Get_Post() {
        Post found = postService.getById(post.getId());

        assertThat(found).usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt")
                .isEqualTo(post);
    }

    @Test
    @DisplayName("존재하지 않는 게시글은 조회에 실패한다.")
    void fail_Get_Post() {
        assertThatThrownBy(() -> postService.getById(Long.MAX_VALUE))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_FOUND_POST.getMessage());
    }

    @Test
    @DisplayName("게시글을 페이징하여 조회할 수 있다.")
    void success_Get_By_Page() {
        Post post1 = new Post(1L, new Title("title1"), new Content("content1"), 1L);
        Post post2 = new Post(2L, new Title("title2"), new Content("content2"), 1L);

        postService.create(post1);
        postService.create(post2);

        PageRequest page = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "id"));

        Page<Post> byPage = postService.getByPage(page);

        assertThat(byPage.getTotalElements()).isEqualTo(3);
        assertThat(byPage.getContent().size()).isEqualTo(2);
        assertThat(byPage.getContent())
                .usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt")
                .isEqualTo(List.of(post2, post1));
    }

    @Test
    @DisplayName("게시글을 id로 삭제할 수 있다.")
    void success_Delete_Post() {
        postService.deleteById(post.getId());

        assertThatThrownBy(() -> postService.getById(post.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(NOT_FOUND_POST.getMessage());
    }
}
