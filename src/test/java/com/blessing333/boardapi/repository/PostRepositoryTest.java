package com.blessing333.boardapi.repository;

import com.blessing333.boardapi.TestDataProvider;
import com.blessing333.boardapi.entity.Post;
import com.blessing333.boardapi.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostRepositoryTest {
    private static final String postTitle = "title";
    private static final String postContent = "content";
    private static final String userName = "content";
    private static final int userAge = 28;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TestDataProvider dataProvider;
    @Autowired
    private EntityManagerFactory emf;
    @Autowired
    private EntityManager em;

    @DisplayName("게시글 저장")
    @Test
    void testPostSave() {
        User user = dataProvider.insertUserToDb(userName, userAge);
        Post post = Post.createNewPost(postTitle, postContent, user);

        postRepository.save(post);

        assertThat(post.getTitle()).isEqualTo(postTitle);
        assertThat(post.getContent()).isEqualTo(postContent);
        assertThat(post.getWriter()).isEqualTo(user);
    }

    @DisplayName("게시글 작성자가 DB에 저장되어있지 않다면 예외가 발생해야한다")
    @Test
    void testPostSaveWithInvalidPoster() {
        User user = User.createUser(userName, userAge);
        Post post = Post.createNewPost(postTitle, postContent, user);

        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> postRepository.save(post));
    }

    @DisplayName("게시글 생성일 기준 내림차순으로 10개씩 페이징하여 조회")
    @Test
    void testPostInquiryWithPaging() {
        User user = dataProvider.insertUserToDb(userName, userAge);
        dataProvider.insert20PostToDB(user);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Post> result = postRepository.findAll(pageable);

        assertThat(result).hasSize(10);
        assertThat(result.getTotalElements()).isEqualTo(20);
        int seq = 20;
        for (Post post : result) {
            assertThat(post.getContent()).isEqualTo(Integer.toString(seq));
            seq--;
        }
    }

    @DisplayName("게시글 수정 테스트(dirty checking)")
    @Test
    void updatePostTest() {
        User user = dataProvider.insertUserToDb(userName, userAge);
        String newTitle = "changed title";
        Post post = Post.createNewPost(postTitle, postContent, user);
        post = postRepository.save(post);

        post.changeTitle(newTitle);

        Optional<Post> found = postRepository.findById(post.getId());
        Assertions.assertDoesNotThrow(() -> {
            Post foundPost = found.orElseThrow();
            assertThat(foundPost.getTitle()).isEqualTo(newTitle);
        });
    }

    @DisplayName("게시글 작성자 정보와 같이 게시글 정보를 조회한다")
    @Test
    void joinTest() {
        PersistenceUnitUtil unitUtil = emf.getPersistenceUnitUtil();
        User user = dataProvider.insertUserToDb(userName, userAge);
        Post post = dataProvider.insertPostToDb(postTitle, postContent, user);
        em.flush();
        em.clear();

        Assertions.assertDoesNotThrow(()->{
            Post found = postRepository.findPostWithMemberById(post.getId()).orElseThrow();
            assertThat(unitUtil.isLoaded(found.getWriter())).isTrue();
        });
    }


}