package com.programmers.springboard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.programmers.springboard.entity.Post;
import com.programmers.springboard.entity.QMember;
import com.programmers.springboard.entity.QPost;
import com.programmers.springboard.request.PostSearchRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<Post> findPostsByCustomCondition(PostSearchRequest request, Pageable pageable) {
		QPost post = QPost.post;
		QMember member = QMember.member;

		// 검색 조건절
		BooleanBuilder whereClause = new BooleanBuilder();
		if (request.title() != null) {
			whereClause.and(post.title.contains(request.title()));
		}

		// 게시글 목록 조회
		List<Post> posts = jpaQueryFactory
			.selectFrom(post)
			.leftJoin(post.member, member).fetchJoin()
			.where(whereClause)
			.orderBy(post.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 게시글 수
		long totalCount = Optional.ofNullable(
			jpaQueryFactory
				.select(post.count())
				.from(post)
				.where(whereClause)
				.fetchOne()
		).orElse(0L);

		return new PageImpl<>(posts, pageable, totalCount);
	}
}
