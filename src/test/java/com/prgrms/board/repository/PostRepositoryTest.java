package com.prgrms.board.repository;

import com.prgrms.board.domain.Post;
import com.prgrms.board.domain.Users;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanup() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    @DisplayName("게시글 생성")
    public void savePost() {

        // Given
        Users user = new Users("lee@test.com", "lee", 10);
        userRepository.save(user);
        // When
        Post newPost = new Post(user, "title", "content");
        Post savedPost = postRepository.save(newPost);
        // Then
        assertThat(newPost).usingRecursiveComparison().isEqualTo(savedPost);
    }

    @Test
    @DisplayName("게시글 조회: 페이징 조회")
    public void pagingPosts() {

        //== given ==//
        Users user = new Users("lee@test.com", "lee", 10);
        userRepository.save(user);
        int pageSize = 10;
        int totalPages = 3;
        for (int i = 0; i < 30; i++) {
            postRepository.save(new Post(user, "Post " + i, "Content " + i));
        }
        //== when ==//
        Pageable pageRequest = PageRequest.of(0, pageSize);
        Page<Post> posts = postRepository.findAllWithUser(pageRequest);
        //== then ==//
        assertThat(posts.getTotalPages()).isEqualTo(totalPages);
        assertThat(posts.getTotalElements()).isEqualTo(30L);
        assertThat(posts.getContent().size()).isEqualTo(pageSize); // 현재 0페이지의 총 개수
        assertThat(posts.getContent().get(0).getTitle()).isEqualTo("Post 0");
    }


    @Test
    @DisplayName("게시글 단건 조회")
    public void findById() {
    
        //== given ==//
        Users user = new Users("lee@test.com", "lee", 10);
        userRepository.save(user);
        Post post = new Post(user, "Test Post", "Test Content");
        postRepository.save(post);
        //== when ==//
        Post foundPost = postRepository.findById(post.getPostId()).orElse(null);

        //== then ==//
        assertThat(foundPost).isNotNull();
        assertThat(foundPost).usingRecursiveComparison().isEqualTo(post);
    }

    @Test
    @DisplayName("게시글 수정")
    public void update() throws InterruptedException {

        //== given ==//
        Users user = new Users("lee@test.com", "lee", 10);
        userRepository.save(user);
        Post post = new Post(user, "title", "content");
        Post savedPost = postRepository.save(post);

        //== when ==//
        savedPost.updatePost("updateTitle", "updateContent");
        Post updatedPost = postRepository.save(savedPost);

        //== then ==//
        assertThat(savedPost).isNotNull();
        assertThat(updatedPost).usingRecursiveComparison().isEqualTo(savedPost);
    }
}