package kdt.cse0518.board.post.repository;

import kdt.cse0518.board.post.entity.Post;
import kdt.cse0518.board.post.factory.PostFactory;
import kdt.cse0518.board.user.entity.User;
import kdt.cse0518.board.user.factory.UserFactory;
import kdt.cse0518.board.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class PostRepositoryTest {

    private User newUser1;
    private Post newPost1;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostFactory postFactory;
    @Autowired
    private UserFactory userFactory;

    @BeforeEach
    void setUp() {
        newUser1 = userFactory.createUser("최승은", 26, "weight training");
        userRepository.save(newUser1);
        newPost1 = postFactory.createPost("제목", "내용", newUser1);
        postRepository.save(newPost1);
    }

    @Test
    @DisplayName("Post가 DB에 저장될 수 있다.")
    @Transactional
    void testSavePost() {
        final Post postEntity = postRepository.findById(1L).get();
        assertThat(postEntity.getTitle(), is("제목"));
        assertThat(postEntity.getContent(), is("내용"));
        assertThat(postEntity.getUser().getName(), is("최승은"));

        final Post newPost2 = postFactory.createPost("제목2", "내용2", newUser1);
        postRepository.save(newPost2);

        final Post postEntity2 = postRepository.findById(2L).get();
        assertThat(postEntity2.getTitle(), is("제목2"));
        assertThat(postEntity2.getContent(), is("내용2"));
        assertThat(postEntity2.getUser().getName(), is("최승은"));

        final List<Post> allPostsEntity = postRepository.findAll();
        assertThat(allPostsEntity.size(), is(2));

        final List<User> allUsersEntity = userRepository.findAll();
        assertThat(allUsersEntity.size(), is(1));
    }

    @Test
    @DisplayName("해당 User가 작성한 Posts를 전체 조회할 수 있다.")
    @Transactional
    void testFindByUser() {
        final Pageable pageable = PageRequest.of(0, 5);
        final Post newPost2 = postFactory.createPost("제목2", "내용2", newUser1);
        postRepository.save(newPost2);
        final Page<Post> postsByUser = postRepository.findByUser(newUser1, pageable);
        assertThat(postsByUser.getContent().size(), is(2));
    }
}