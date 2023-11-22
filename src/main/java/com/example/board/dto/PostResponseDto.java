package com.example.board.dto;

import com.example.board.exception.BaseException;
import com.example.board.exception.ErrorMessage;
import com.example.board.model.Post;

public record PostResponseDto(
        Long postId,
        String userName,
        String title
) {
    public static PostResponseDto from(Post post) {
        postResponseValidate(post);
        return new PostResponseDto(post.getId(), post.getTitle(), post.getUser().getName());
    }

    private static void postResponseValidate(Post post) {
        if (post.getId() == null) {
            throw new BaseException(ErrorMessage.POST_NOT_FOUND);
        }
        if (post.getTitle().isBlank() || (post.getTitle().length() > 20)) {
            throw new BaseException(ErrorMessage.WRONG_TITLE_VALUE);
        }
        if (post.getUser() == null) {
            throw new BaseException(ErrorMessage.USER_NOT_FOUND);
        }
        if (post.getUser().getName().isBlank()) {
            throw new BaseException(ErrorMessage.WRONG_USER_NAME);
        }
    }
}
