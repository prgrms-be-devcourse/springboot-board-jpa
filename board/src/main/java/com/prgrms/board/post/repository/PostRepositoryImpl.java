package com.prgrms.board.post.repository;

import com.prgrms.board.post.dto.PostResponse;
import com.prgrms.board.post.dto.QPostResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static com.prgrms.board.post.domain.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public Slice<PostResponse> findPostPageable(String keyword, Pageable pageable) {
        List<PostResponse> posts = queryFactory
                .select(new QPostResponse(
                        post.postId,
                        post.title,
                        post.content,
                        post.createdBy
                ))
                .from(post)
                .where(keywordContains(keyword))
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
        boolean hasNext = false;
        if (posts.size() > pageable.getPageSize()) {
            posts.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(posts, pageable, hasNext);
    }

    private BooleanExpression keywordContains(String keyword) {
        return ObjectUtils.isEmpty(keyword) ? null : post.content.contains(keyword);
    }
}
