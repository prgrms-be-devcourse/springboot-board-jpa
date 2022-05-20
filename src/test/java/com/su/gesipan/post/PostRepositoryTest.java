package com.su.gesipan.post;

import com.su.gesipan.user.User;
import com.su.gesipan.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static com.su.gesipan.helper.Helper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    @Nested
    class 게시글_저장 {
        @Test
        void 유저를_생성자에_넘겨_저장할_수_있다_단_save_호출_해야_됨() {
            var user = makeUserAndSave();
            var post = Post.of("제목", "본문", user);

            postRepository.save(post);

            assertAll(
                    () -> assertThat(postRepository.count()).isOne(),
                    () -> assertThat(userRepository.count()).isOne()
            );
        }

        @Test
        void 유저통해서_저장하면_save_호출_안_해도_됨() {
            var user = makeUserAndSave();
            var post = Post.of("제목", "본문");

            user.addPost(post);

            assertAll(
                    () -> assertThat(postRepository.findAll()).contains(post),
                    () -> assertThat(userRepository.findAll()).contains(user),
                    () -> assertThat(post.getUser()).usingRecursiveComparison().isEqualTo(user)
            );
        }
    }

    @Nested
    class 게시글_조회 {
        @Test
        void 페이징() {
            var user = makeUserAndSave();
            var post = makePost();
            user.addPost(post);

            var pagePost = postRepository.findAll(PageRequest.of(0, 10));

            assertAll(
                    () -> assertThat(pagePost.getTotalElements()).isEqualTo(1),
                    () -> assertThat(pagePost.getTotalPages()).isEqualTo(1),
                    () -> assertThat(pagePost.isFirst()).isTrue(),
                    () -> assertThat(pagePost.isLast()).isTrue(),
                    () -> assertThat(pagePost.isEmpty()).isFalse(),
                    () -> assertThat(pagePost.hasContent()).isTrue(),
                    () -> assertThat(pagePost.hasPrevious()).isFalse(),
                    () -> assertThat(pagePost.hasNext()).isFalse()
            );
        }

        @Test
        void 단건() {
            var user = makeUserAndSave();
            var post = makePost();
            user.addPost(post);

            var posts = postRepository.findAll();
            assertThat(posts).contains(post);
        }
    }

    @Nested
    class 게시글_수정 {
        @Test
        void 제목과_본문을_수정할_수_있다_Dirty_Checking() {
            var user = makeUserAndSave();
            var post = makePost();
            user.addPost(post);

            var findPost = postRepository.findByUserId(user.getId()).orElseThrow();
            findPost.editTitle("제목수정");
            findPost.editContent("본문수정");

            var editPost = postRepository.findById(findPost.getId()).get();
            assertAll(
                    () -> assertThat(editPost.getTitle()).isEqualTo("제목수정"),
                    () -> assertThat(editPost.getContent()).isEqualTo("본문수정")
            );
        }
    }

    @Nested
    class 게시글_삭제 {

        User user;
        Post post;

        @BeforeEach
        void USER_AND_POST_SETUP() {
            user = User.of("su", 24L, "게임");
            post = Post.of("제목", "본문");
            user.addPost(post);
        }

        @Test
        void 유저가_포스트를_가지고_있으면_포스트는_혼자_삭제되지_않는다() {
            assertThat(postRepository.count()).isOne();
            assertThat(userRepository.count()).isOne();

            postRepository.deleteById(post.getId());
            // USER 쪽 참조가 끊어지지 않았으므로 무의미하다 -> CASCADE

            assertAll(
                    () -> assertThat(postRepository.findAll()).contains(post),
                    () -> assertThat(userRepository.findAll()).contains(user)
            );
        }

        @Test
        void 유저가_삭제되면_포스트도_삭제된다_CASCADE() {
            assertThat(postRepository.count()).isOne();
            assertThat(userRepository.count()).isOne();

            userRepository.deleteById(user.getId());

            assertAll(
                    () -> assertThat(userRepository.count()).isZero(),
                    () -> assertThat(postRepository.count()).isZero()
            );
        }

        @Test
        void 유저가_삭제되지_않아도_유저가_포스트를_삭제하면_자동으로_포스트가_지워진다_ORPHAN() {
            assertThat(postRepository.count()).isOne();
            assertThat(userRepository.count()).isOne();

            user.deletePost(post);

            assertAll(
                    () -> assertThat(userRepository.count()).isOne(),
                    () -> assertThat(postRepository.count()).isZero()
            );
        }
    }

    User makeUserAndSave() {
        return userRepository.save(User.of("su", 24L, "게임"));
    }
}