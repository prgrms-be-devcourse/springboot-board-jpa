package devcourse.board.domain.post;

import devcourse.board.domain.post.model.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {

    private final EntityManager em;

    public PostRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Post post) {
        em.persist(post);
    }

    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(em.find(Post.class, id));
    }


    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public List<Post> findWithPaging(int startPosition, int maxResultCount) {
        return em.createQuery("select p from Post p order by p.id asc", Post.class)
                .setFirstResult(startPosition)
                .setMaxResults(maxResultCount)
                .getResultList();
    }
}
