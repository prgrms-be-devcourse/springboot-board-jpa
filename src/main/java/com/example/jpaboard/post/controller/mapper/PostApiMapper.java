package com.example.jpaboard.post.controller.mapper;

import com.example.jpaboard.post.controller.dto.FindAllApiRequest;
import com.example.jpaboard.post.controller.dto.SaveApiRequest;
import com.example.jpaboard.post.controller.dto.UpdateApiRequest;
import com.example.jpaboard.post.service.dto.FindAllRequest;
import com.example.jpaboard.post.service.dto.SaveRequest;
import com.example.jpaboard.post.service.dto.UpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class PostApiMapper {

    public FindAllRequest toFindAllRequest(FindAllApiRequest findAllApiRequest) {

        return new FindAllRequest(findAllApiRequest.title(), findAllApiRequest.content());
    }

    public UpdateRequest toUpdateRequest(UpdateApiRequest updateApiRequest) {
        return new UpdateRequest(updateApiRequest.title(),
                updateApiRequest.content(),
                updateApiRequest.memberId());
    }

    public SaveRequest toSaveRequest(SaveApiRequest saveApiRequest) {
        return new SaveRequest(saveApiRequest.memberId(),
                saveApiRequest.title(),
                saveApiRequest.content());
    }

}
