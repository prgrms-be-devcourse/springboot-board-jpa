package com.programmers.springbootboard.post.converter;

import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.post.domain.Post;
import com.programmers.springbootboard.post.domain.vo.Content;
import com.programmers.springbootboard.post.domain.vo.Title;
import com.programmers.springbootboard.post.dto.PostDeleteResponse;
import com.programmers.springbootboard.post.dto.PostDetailResponse;
import com.programmers.springbootboard.post.dto.PostInsertRequest;
import com.programmers.springbootboard.post.dto.PostUpdateRequest;
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

    public PostInsertRequest toPostInsertRequest(Email email, Title title, Content content) {
        return PostInsertRequest.builder()
                .email(email.getEmail())
                .title(title.getTitle())
                .content(content.getContent())
                .build();
    }

    public PostDetailResponse toPostDetailResponse(Post post) {
        return PostDetailResponse.builder()
                .id(post.getId())
                .title(post.getTitle().getTitle())
                .content(post.getContent().getContent())
                .email(post.getMember().getEmail().getEmail())
                .build();
    }

    public PostUpdateRequest toPostUpdateRequest(Email email, Title title, Content content) {
        return PostUpdateRequest.builder()
                .email(email.getEmail())
                .title(title.getTitle())
                .content(content.getContent())
                .build();
    }

    public PostDeleteResponse toPostDeleteResponse(Long id, Email email) {
        return PostDeleteResponse.builder()
                .id(id)
                .email(email.getEmail())
                .build();
    }
}
