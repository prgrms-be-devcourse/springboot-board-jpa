package com.example.jpaboard.post.controller.mapper;

import com.example.jpaboard.post.controller.dto.PostFindApiRequest;
import com.example.jpaboard.post.controller.dto.PostSaveApiRequest;
import com.example.jpaboard.post.controller.dto.PostUpdateApiRequest;
import com.example.jpaboard.post.service.dto.PostFindRequest;
import com.example.jpaboard.post.service.dto.PostSaveRequest;
import com.example.jpaboard.post.service.dto.PostUpdateRequest;

import org.springframework.stereotype.Component;

@Component
public class PostApiMapper {

    public PostFindRequest toFindAllRequest(PostFindApiRequest postRetrieveApiRequest) {

        return new PostFindRequest(postRetrieveApiRequest.title(), postRetrieveApiRequest.content());
    }

    public PostUpdateRequest toUpdateRequest(PostUpdateApiRequest postUpdateApiRequest) {
        return new PostUpdateRequest(postUpdateApiRequest.title(),
                postUpdateApiRequest.content(),
                postUpdateApiRequest.memberId());
    }

    public PostSaveRequest toSaveRequest(PostSaveApiRequest postSaveApiRequest) {
        return new PostSaveRequest(postSaveApiRequest.memberId(),
                postSaveApiRequest.title(),
                postSaveApiRequest.content());

    }

}
