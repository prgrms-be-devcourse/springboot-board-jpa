package com.example.board.repository.post;

import com.example.board.domain.Post;
import com.example.board.dto.request.post.PostSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.board.domain.QPost.post;
import static com.example.board.domain.QUser.user;

@Repository
public class PostQuerydslRepositoryImpl implements PostQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public PostQuerydslRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Post> findAll(PostSearchCondition condition, Pageable pageable) {

        JPAQuery<Post> query = queryFactory
                .selectFrom(post)
                .leftJoin(post.author, user)
                .where(createdAtGoe(condition.createdAtFrom()),
                        createdAtLoe(condition.createdAtTo()));

        List<Post> posts = query
                .orderBy(condition.sortType() == null ? post.id.desc() :
                        switch (condition.sortType()) {
                            case LATEST -> post.id.desc();
                            case OLDEST -> post.id.asc();
                        })
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults()
                .getResults();

        return PageableExecutionUtils.getPage(posts, pageable, query::fetchCount);
    }

    private BooleanExpression createdAtGoe(LocalDateTime createdAt) {
        return createdAt != null ? post.createdAt.goe(createdAt) : null;
    }

    private BooleanExpression createdAtLoe(LocalDateTime createdAt) {
        return createdAt != null ? post.createdAt.loe(createdAt) : null;
    }
}
