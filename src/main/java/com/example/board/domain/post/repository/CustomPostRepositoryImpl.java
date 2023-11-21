package com.example.board.domain.post.repository;

import static com.example.board.domain.post.entity.QPost.post;
import static org.springframework.util.StringUtils.hasText;

import com.example.board.domain.post.dto.PostPageCondition;
import com.example.board.domain.post.entity.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class CustomPostRepositoryImpl implements CustomPostRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Post> findPostsByCondition(PostPageCondition condition) {
        Pageable pageable = condition.getPageable();

        List<Post> content = queryFactory.selectFrom(post)
            .where(
                titleEq(condition.getTitle()),
                emailEq(condition.getEmail())
            )
            .orderBy(post.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(post.count())
            .from(post)
            .where(titleEq(condition.getTitle()),
                emailEq(condition.getEmail()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression titleEq(String title) {
        return hasText(title) ? post.title.contains(title) : null;
    }

    private BooleanExpression emailEq(String email) {
        return hasText(email) ? post.member.email.contains(email) : null;
    }
}
