package com.programmers.board.service;

import com.programmers.board.dto.Convertor;
import com.programmers.board.dto.PostRequestDto;
import com.programmers.board.dto.PostResponseDto;
import com.programmers.board.entity.Post;
import com.programmers.board.entity.User;
import com.programmers.board.repository.PostRepository;
import com.programmers.board.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class PostServiceTest {
    @Autowired
    private EntityManagerFactory emf;
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    private Long userId;
    private User userEntity;

    @BeforeEach
    void setUser() {
        User user = new User("test-user", 20, "test-hobby");
        userEntity = userRepository.save(user);
        userId = userEntity.getId();
    }

    @AfterEach
    void clear() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void PostSaveTest() {
        PostRequestDto postRequestDto = new PostRequestDto("test-title", "test-content", userId);
        Long postId = postService.save(postRequestDto);
        assertThat(postId).isNotNull();
        Optional<Post> postOptional = postRepository.findById(postId);
        assertThat(postOptional.isEmpty()).isFalse();
        assertThat(postOptional.get().getId()).isEqualTo(postId);
    }

    @Test
    void PostGetTest() {
        Post post = new Post("test-title2", "test-content2");
        post.setUser(userEntity);
        Post entity = postRepository.save(post);
        EntityManager em = emf.createEntityManager();
        em.detach(entity);

        PostResponseDto postResponseDto = postService.find(entity.getId());
        assertThat(postResponseDto).usingRecursiveComparison().isEqualTo(Convertor.postResponseConvertor(entity));
    }

    @Test
    void PostUpdateTest() {
        Post post = new Post("test-title3", "test-content3");
        post.setUser(userEntity);
        Post entity1 = postRepository.save(post);
        EntityManager em = emf.createEntityManager();
        em.detach(entity1);
        entity1.setTitle("test-updatedTitle");
        entity1.setContent("test-updatedContent");
        Post entity2 = postRepository.save(entity1);
        em.detach(entity2);

        PostResponseDto postResponseDto2 = postService.find(entity2.getId());
        assertThat(postResponseDto2).usingRecursiveComparison().isEqualTo(Convertor.postResponseConvertor(entity1));
    }

    @Test
    void PostGetAllTest() {
        Post post1 = new Post("test-title3", "test-content3");
        Post post2 = new Post("test-title4", "test-content4");
        post1.setUser(userEntity);
        post2.setUser(userEntity);
        Post entity1 = postRepository.save(post1);
        Post entity2 = postRepository.save(post2);
        EntityManager em = emf.createEntityManager();
        em.detach(entity1);
        em.detach(entity2);

        PageRequest page = PageRequest.of(0, 2);
        Page<PostResponseDto> all = postService.findAll(page);
        assertThat(all.getTotalElements()).isEqualTo(2);
    }
}