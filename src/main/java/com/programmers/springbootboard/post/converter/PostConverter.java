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
    public PostInsertBundle toPostInsertServiceDto(PostInsertRequest dto) {
        return PostInsertBundle.builder()
                .email(new Email(dto.getEmail()))
                .title(new Title(dto.getTitle()))
                .content(new Content(dto.getContent()))
                .build();
    }

    public Post toPost(PostInsertBundle request) {
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    public PostInsertResponse toPostInsertResponseDto(Post post) {
        return PostInsertResponse.builder()
                .id(post.getId())
                .title(post.getTitle().getTitle())
                .content(post.getContent().getContent())
                .build();
    }

    public PostDeleteBundle toPostDeleteServiceDto(Long id, PostDeleteRequest dto) {
        return PostDeleteBundle.builder()
                .email(new Email(dto.getEmail()))
                .id(id)
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

    public PostUpdateBundle toPostUpdateServiceDto(Long id, PostUpdateRequest requestDto) {
        return PostUpdateBundle.builder()
                .email(new Email(requestDto.getEmail()))
                .id(id)
                .title(new Title(requestDto.getTitle()))
                .content(new Content(requestDto.getContent()))
                .build();
    }

    public PostUpdateResponse toPostUpdateResponseDto(Post post) {
        return PostUpdateResponse.builder()
                .email(post.getMember().getEmail().getEmail())
                .id(post.getId())
                .title(post.getTitle().getTitle())
                .content(post.getContent().getContent())
                .build();
    }

    public PostFindBundle toPostFindServiceDto(Long id) {
        return PostFindBundle.builder()
                .id(id)
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
