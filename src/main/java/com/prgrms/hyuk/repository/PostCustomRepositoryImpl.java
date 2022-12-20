package com.prgrms.hyuk.repository;

import com.prgrms.hyuk.domain.post.Post;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final EntityManager entityManager;

    public PostCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Post> findAll(int offset, int limit) {
        return entityManager.createQuery(
                "select p from Post p" +
                    " join fetch p.user u", Post.class)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
    }
}
