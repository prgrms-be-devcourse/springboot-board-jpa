package com.prgrms.springbootboardjpa.facade;

import com.prgrms.springbootboardjpa.dto.PostRequest;
import com.prgrms.springbootboardjpa.dto.PostResponse;
import com.prgrms.springbootboardjpa.dto.UserResponse;
import com.prgrms.springbootboardjpa.service.PostService;
import com.prgrms.springbootboardjpa.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Facade
@AllArgsConstructor
public class PostFacade {

    private final UserService userService;

    private final PostService postService;

    @Transactional
    public PostResponse save(PostRequest postRequest) {
        try {
            UserResponse user = userService.getOne(Long.parseLong(postRequest.getUser().toString()));
            postRequest.setUser(user);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("올바른 사용자 식별값이 필요합니다.");
        }
        return postService.save(postRequest);
    }
}
