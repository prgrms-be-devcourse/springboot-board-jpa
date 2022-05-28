package org.prgrms.board.domain.post;

import static org.prgrms.board.domain.post.QPost.*;
import static org.prgrms.board.domain.user.QUser.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class PostQueryRepository {

	private final JPAQueryFactory queryFactory;

	public PostQueryRepository(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	public List<Post> findAll(long offset, int limit) {
		return queryFactory.select(post)
			.from(post)
			.innerJoin(post.writer, user)
			.fetchJoin()
			.orderBy(post.createdAt.desc())
			.offset(offset)
			.limit(limit)
			.fetch();
	}
}