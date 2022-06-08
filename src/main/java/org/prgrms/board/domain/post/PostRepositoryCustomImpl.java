package org.prgrms.board.domain.post;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryCustomImpl {

	private final EntityManager em;

	public PostRepositoryCustomImpl(EntityManager em) {
		this.em = em;
	}

	public List<Post> findAll(long offset, int limit) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Post> cq = cb.createQuery(Post.class);

		Root<Post> post = cq.from(Post.class);
		post.fetch("writer", JoinType.INNER);

		Order idDesc = cb.desc(post.get("id"));

		cq.orderBy(idDesc);

		return em.createQuery(cq)
			.setFirstResult((int)offset)
			.setMaxResults(limit)
			.getResultList();
	}
}