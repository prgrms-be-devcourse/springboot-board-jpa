//package devcource.hihi.boardjpa.repository;
//
//import devcource.hihi.boardjpa.domain.Post;
//import devcource.hihi.boardjpa.domain.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.lang.reflect.Member;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class PostRepositoryTest {
//    @Autowired
//    private PostRepository postRepository;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Test
//    void findById() {
//        //given
//        User user = new User("testUser",25, "dance");
//        Post post = new Post("test","testtt");
//        entityManager.persistAndFlush(post);
//        //when
//        Optional<Post> findPost = postRepository.findById(post.getId());
//        //then
//        assertThat(findPost).isPresent();
//    }
//
//    @Test
//    void save() {
//        // given
//        Post post = new Post("test","testtt");
//
//        // when
//        postRepository.save(post);
//        entityManager.flush();
//
//        // then
//        Post result = entityManager.find(Post.class, post.getId());
//        assertThat(result).isNotNull();
//    }
//}