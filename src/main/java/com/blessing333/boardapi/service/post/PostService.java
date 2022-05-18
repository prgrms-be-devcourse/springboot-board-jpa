package com.blessing333.boardapi.service.post;

import com.blessing333.boardapi.controller.dto.PostCreateCommand;
import com.blessing333.boardapi.controller.dto.PostInformation;
import com.blessing333.boardapi.controller.dto.PostUpdateCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostInformation loadPostById(Long id);

    Page<PostInformation> loadPostsWithPaging(Pageable pageable);

    PostInformation registerPost(PostCreateCommand command);

    PostInformation updatePost(PostUpdateCommand command);
}
