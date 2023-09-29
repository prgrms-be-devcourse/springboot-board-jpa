package com.blackdog.springbootBoardJpa.domain.post.controller.converter;

import com.blackdog.springbootBoardJpa.domain.post.controller.dto.PostCreateDto;
import com.blackdog.springbootBoardJpa.domain.post.controller.dto.PostUpdateDto;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostCreateRequest;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class PostControllerConverter {

    public PostCreateRequest toCreateRequest(PostCreateDto dto) {
        return new PostCreateRequest(
                dto.title(),
                dto.content()
        );
    }

    public PostUpdateRequest toUpdateRequest(PostUpdateDto dto) {
        return new PostUpdateRequest(
                dto.title(),
                dto.content()
        );
    }
}
