package com.prgrms.board.post.controller.dto;

import com.prgrms.board.post.service.dto.PostDetailedParam;
import com.prgrms.board.post.service.dto.PostDetailedResult;
import com.prgrms.board.post.service.dto.PostDetailedResults;
import com.prgrms.board.post.service.dto.PostShortResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostControllerConverter {
    public PostDetailedParam toPostDetailedParam(PostDetailedRequest request) {
        return new PostDetailedParam(
                request.title(),
                request.content(),
                request.userId()
        );
    }

    public PostShortResponse toPostShortResponse(PostShortResult result) {
        return new PostShortResponse(
                result.id()
        );
    }

    public PostDetailedResponse toPostDetailedResponse(PostDetailedResult result) {
        return new PostDetailedResponse(
                result.id(),
                result.title(),
                result.content(),
                result.createdBy(),
                result.createdDate()
        );
    }

    public PostDetailedResponses toPostDetailedResponses(PostDetailedResults results) {
        List<PostDetailedResponse> responseList =
                results.list().stream()
                        .map(this::toPostDetailedResponse)
                        .toList();
        return new PostDetailedResponses(responseList);
    }
}
