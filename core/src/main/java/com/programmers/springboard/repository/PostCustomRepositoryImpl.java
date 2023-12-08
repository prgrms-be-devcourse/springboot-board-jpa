package com.programmers.springboard.repository;

import com.programmers.springboard.entity.Post;
import com.programmers.springboard.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository{

	private final JPAQueryFactory jpaQueryFactory;
	private static final Integer PAGEOFFSET = 10;

	public List<Post> getPosts(Integer page){
		QPost post = QPost.post;

		List<Post> posts = jpaQueryFactory
			.selectFrom(post)
			.offset((long) (page-1)*PAGEOFFSET)
			.limit(PAGEOFFSET)
			.fetch();

		return posts;
	}
}
