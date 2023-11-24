package com.example.board.repository.post;

import com.example.board.domain.Post;
import com.example.board.dto.request.post.PostSearchCondition;
import com.example.board.dto.request.post.SortType;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
                .where(createFilterCondition(condition));

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .where(createFilterCondition(condition));

        List<Post> posts = query
                .orderBy(createSortCondition(condition.sortType()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(posts, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> createSortCondition(SortType sortType) {
        if (sortType == null) {
            return post.id.desc();
        }
        return switch (sortType) {
            case LATEST -> post.id.desc();
            case OLDEST -> post.id.asc();
        };
    }

    private BooleanExpression[] createFilterCondition(PostSearchCondition condition) {
        LocalDate createdAtFrom = condition.createdAtFrom();
        LocalDate createdAtTo = condition.createdAtTo();
        return new BooleanExpression[]{
                createdAtGoe(createdAtFrom != null ? createdAtFrom.atStartOfDay() : null),
                createdAtLoe(createdAtTo != null ? createdAtTo.atTime(23, 59, 59) : null)
        };
    }

    private BooleanExpression createdAtGoe(LocalDateTime createdAt) {
        return createdAt != null ? post.createdAt.goe(createdAt) : null;
    }

    private BooleanExpression createdAtLoe(LocalDateTime createdAt) {
        return createdAt != null ? post.createdAt.loe(createdAt) : null;
    }
}
