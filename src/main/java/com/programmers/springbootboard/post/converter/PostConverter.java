package com.programmers.springbootboard.post.converter;

import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.post.domain.Post;
import com.programmers.springbootboard.post.domain.vo.Content;
import com.programmers.springbootboard.post.domain.vo.Title;
import com.programmers.springbootboard.post.dto.request.PostDeleteRequest;
import com.programmers.springbootboard.post.dto.request.PostInsertRequest;
import com.programmers.springbootboard.post.dto.response.PostDeleteResponse;
import com.programmers.springbootboard.post.dto.response.PostDetailResponse;
import com.programmers.springbootboard.post.dto.response.PostInsertResponse;
import com.programmers.springbootboard.post.dto.request.PostUpdateRequest;
import com.programmers.springbootboard.post.dto.response.PostUpdateResponse;
import com.programmers.springbootboard.post.dto.bundle.PostDeleteBundle;
import com.programmers.springbootboard.post.dto.bundle.PostFindBundle;
import com.programmers.springbootboard.post.dto.bundle.PostInsertBundle;
import com.programmers.springbootboard.post.dto.bundle.PostUpdateBundle;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public PostInsertBundle toPostInsertBundle(PostInsertRequest request) {
        return PostInsertBundle.builder()
                .email(new Email(request.getEmail()))
                .title(new Title(request.getTitle()))
                .content(new Content(request.getContent()))
                .build();
    }

    public Post toPost(PostInsertBundle request) {
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    public PostInsertResponse toPostInsertResponse(Post post) {
        return PostInsertResponse.builder()
                .postId(post.getId())
                .title(post.getTitle().getTitle())
                .content(post.getContent().getContent())
                .build();
    }

    public PostDeleteBundle toPostDeleteBundle(Long postId, PostDeleteRequest dto) {
        return PostDeleteBundle.builder()
                .email(new Email(dto.getEmail()))
                .postId(postId)
                .build();
    }

    public PostDetailResponse toPostDetailResponse(Post post) {
        return PostDetailResponse.builder()
                .postId(post.getId())
                .title(post.getTitle().getTitle())
                .content(post.getContent().getContent())
                .email(post.getMember().getEmail().getEmail())
                .build();
    }

    public PostUpdateBundle toPostUpdateBundle(Long postId, PostUpdateRequest requestDto) {
        return PostUpdateBundle.builder()
                .email(new Email(requestDto.getEmail()))
                .postId(postId)
                .title(new Title(requestDto.getTitle()))
                .content(new Content(requestDto.getContent()))
                .build();
    }

    public PostUpdateResponse toPostUpdateResponse(Post post) {
        return PostUpdateResponse.builder()
                .email(post.getMember().getEmail().getEmail())
                .postId(post.getId())
                .title(post.getTitle().getTitle())
                .content(post.getContent().getContent())
                .build();
    }

    public PostFindBundle toPostFindBundle(Long postId) {
        return PostFindBundle.builder()
                .postId(postId)
                .build();
    }

    public PostDeleteResponse toPostDeleteResponse(Long postId, Email email) {
        return PostDeleteResponse.builder()
                .postId(postId)
                .email(email.getEmail())
                .build();
    }
}
