package com.blessing333.boardapi.service.post;

import com.blessing333.boardapi.controller.dto.PostCreateCommands;
import com.blessing333.boardapi.controller.dto.PostInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostInformation loadPostById(Long id);

    Page<PostInformation> loadPostsWithPaging(Pageable pageable);

    PostInformation registerPost(PostCreateCommands commands);
}
