package com.blessing333.boardapi.service.post;

import com.blessing333.boardapi.controller.dto.PostCreateCommands;
import com.blessing333.boardapi.controller.dto.PostInformation;

public interface PostService {
    PostInformation loadPostById(Long id);

    PostInformation registerPost(PostCreateCommands commands);
}
