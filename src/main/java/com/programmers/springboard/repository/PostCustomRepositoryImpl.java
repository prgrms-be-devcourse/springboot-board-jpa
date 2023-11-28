package com.programmers.springboard.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.programmers.springboard.entity.Post;
import com.programmers.springboard.entity.QMember;
import com.programmers.springboard.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {

	private static final Integer PAGEOFFSET = 10;
	private final JPAQueryFactory jpaQueryFactory;

	public List<Post> getPosts(Integer page) {
		QPost post = QPost.post;
		QMember member = QMember.member;

		return jpaQueryFactory
			.selectFrom(post)
			.leftJoin(post.member, member).fetchJoin()
			.offset((long)(page - 1) * PAGEOFFSET)
			.limit(PAGEOFFSET)
			.fetch();
	}
}
