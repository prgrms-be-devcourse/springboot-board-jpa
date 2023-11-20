package com.example.board.repository;

import com.example.board.domain.Post;
import com.example.board.domain.QUser;
import com.example.board.dto.request.PostSearchCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import static com.example.board.domain.QPost.post;
import static com.example.board.domain.QUser.user;

@Repository
public class PostQuerydslRepositoryImpl implements PostQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public PostQuerydslRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Post> findAll(PostSearchCondition condition, Pageable pageable) {
        JPAQuery<Post> query = jpaQueryFactory.selectFrom(post);
        BooleanBuilder builder = new BooleanBuilder();
        QUser author = new QUser("author");

        if (condition.title() != null) {
            builder.and(post.title.contains(condition.title()));
        }

        if (condition.content() != null) {
            builder.and(post.content.contains(condition.content()));
        }

        if (condition.authorName() != null) {
            query.leftJoin(author).on(user.name.contains(condition.authorName()));
        }

        query
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(query.fetch(), pageable, () -> query.fetchCount());
    }
}
