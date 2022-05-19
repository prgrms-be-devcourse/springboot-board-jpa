package org.prgrms.board.domain.user.service;

import org.junit.jupiter.api.Test;
import org.prgrms.board.domain.post.Service.PostService;
import org.prgrms.board.domain.post.exception.PostException;
import org.prgrms.board.domain.post.request.PostCreateRequest;
import org.prgrms.board.domain.user.exception.UserException;
import org.prgrms.board.domain.user.requset.UserCreateRequest;
import org.prgrms.board.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Test
    void 유저가_삭제되면_유저가_작성한_게시글도_삭제된다(){
        //given
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .age(27)
                .email("dbslzld15@naver.com")
                .firstName("우진")
                .lastName("박")
                .password("kkkk13804943@")
                .build();
        long userId = userService.save(userCreateRequest);
        PostCreateRequest postCreateRequest = new PostCreateRequest("제목입니다.", "본문입니다.");
        long postId = postService.save(postCreateRequest, userId);
        //when
        userService.delete(userId);
        //then
        assertThatThrownBy(() -> userService.findById(userId))
                .isInstanceOf(UserException.class)
                .hasMessage(ErrorCode.USER_NOT_EXIST.getMessage());
        assertThatThrownBy(() -> postService.findById(postId))
                .isInstanceOf(PostException.class)
                .hasMessage(ErrorCode.POST_NOT_EXIST.getMessage());
    }
}
