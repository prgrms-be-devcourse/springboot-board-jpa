package com.programmers.springbootboard.post.converter;

import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.post.domain.Post;
import com.programmers.springbootboard.post.domain.vo.Content;
import com.programmers.springbootboard.post.domain.vo.Title;
import com.programmers.springbootboard.post.dto.PostDetailResponse;
import com.programmers.springbootboard.post.dto.PostInsertRequest;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public Post toPost(PostInsertRequest request, Member member) {
        return Post.builder()
                .title(new Title(request.getTitle()))
                .content(new Content(request.getContent()))
                .member(member)
                .build();
    }

    public PostDetailResponse toPostDetailResponse(Post post) {
        return PostDetailResponse.builder()
                .title(post.getTitle().getTitle())
                .content(post.getContent().getContent())
                .email(post.getMember().getEmail().getEmail())
                .build();
    }
}
