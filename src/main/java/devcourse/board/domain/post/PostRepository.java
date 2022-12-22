package devcourse.board.domain.post;

import devcourse.board.domain.post.model.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PostRepository {

    private final EntityManager em;

    public PostRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Post post) {
        em.persist(post);
    }

    public Post findOne(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    // 페이징
}
