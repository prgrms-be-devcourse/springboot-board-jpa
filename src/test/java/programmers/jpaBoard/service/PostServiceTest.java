package programmers.jpaBoard.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import programmers.jpaBoard.dto.PostResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Test
    @DisplayName("생성하고자 하는 post의 내용이 저장된 post의 내용과 같아야 한다")
    void createTest() {
        String title = "title";
        String content = "content";
        PostResponse response = postService.create(title, content);

        assertThat(response.title()).isEqualTo(title);
        assertThat(response.content()).isEqualTo(content);
        assertThat(response.id()).isNotNull();
    }

    @Test
    @DisplayName("post를 수정하고 난 뒤에는 수정된 결과를 응답해야 한다")
    void updateTest() {
        String title = "title";
        String content = "content";
        PostResponse response = postService.create(title, content);

        String updatedTitle = "updateTitle";
        String updatedContent = "updateContent";
        PostResponse updatedPost = postService.update(response.id(), updatedTitle, updatedContent);

        assertThat(updatedPost.title()).isEqualTo(updatedTitle);
        assertThat(updatedPost.content()).isEqualTo(updatedContent);
    }

    @Test
    @DisplayName("특정한 post와 그 id를 이용해 불러온 post의 내용이 같아야 한다")
    void getTest() {
        String title = "title";
        String content = "content";
        PostResponse response = postService.create(title, content);

        PostResponse post = postService.getPost(response.id());

        assertThat(response).usingRecursiveComparison().isEqualTo(post);
    }
}
