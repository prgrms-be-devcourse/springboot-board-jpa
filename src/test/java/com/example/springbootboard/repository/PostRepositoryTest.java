package com.example.springbootboard.repository;

import com.example.springbootboard.dto.PostDto;
import com.example.springbootboard.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.example.springbootboard.dto.Converter.dtoToPost;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {
    @Autowired
    PostRepository repository;

    @BeforeEach
    void setUp(){
        repository.deleteAll();
    }

    @Test
    @DisplayName("게시물 생성 테스트")
    void postSaveTest() {
        // Given
        // PostDto dto = PostDto.builder().title("test_title").content("test_content").build();
        //repository.save(dtoToPost(dto));
        repository.save(Post.builder().title("test_title").content("test_content").build());

        // When
        Optional<Post> post = repository.findById(0L);

        // Then
        assertThat(post).isPresent();
        assertThat(post.get().getTitle()).isEqualTo("test_title");
        assertThat(post.get().getContent()).isEqualTo("test_content");
    }
}
