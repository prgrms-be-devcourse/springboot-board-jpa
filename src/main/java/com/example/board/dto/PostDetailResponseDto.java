package com.example.board.dto;

import com.example.board.exception.BaseException;
import com.example.board.exception.ErrorMessage;
import com.example.board.model.Post;

public record PostDetailResponseDto(
        Long postId,
        Long userId,
        String userName,
        String title,
        String contents
) {

    public static PostDetailResponseDto from(Post post) {
        postDetailResponseValidate(post);
        return new PostDetailResponseDto(
                post.getId(),
                post.getUser().getId(),
                post.getUser().getName(),
                post.getTitle(),
                post.getContents());
    }

    private static void postDetailResponseValidate(Post post) {
        if (post.getId() == null) {
            throw new BaseException(ErrorMessage.POST_NOT_FOUND);
        }
        if (post.getUser() == null) {
            throw new BaseException(ErrorMessage.USER_NOT_FOUND);
        }
        if (post.getUser().getId() == null) {
            throw new BaseException(ErrorMessage.WRONG_USER_ID);
        }
        if (post.getTitle().isBlank() || (post.getTitle().length() > 20)) {
            throw new BaseException(ErrorMessage.WRONG_TITLE_VALUE);
        }
        if (post.getContents().isBlank()) {
            throw new BaseException(ErrorMessage.WRONG_CONTENTS_VALUE);
        }
        if (post.getUser().getName().isBlank()) {
            throw new BaseException(ErrorMessage.WRONG_USER_NAME);
        }
    }
}
