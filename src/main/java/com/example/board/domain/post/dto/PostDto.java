package com.example.board.domain.post.dto;

import org.hibernate.validator.constraints.Length;

import com.example.board.domain.post.entity.Post;
import com.example.board.domain.user.dto.UserDto;

import jakarta.validation.constraints.NotNull;

public class PostDto {

    public record CreatePostRequest(@NotNull(message = "{exception.entity.user.id}") Long userId,
                                    @NotNull(message = "{exception.entity.post.title.null}") @Length(min = 1, message = "{exception.entity.post.title.length}") String title,
                                    @NotNull(message = "{exception.entity.post.content.null}") @Length(min = 1, message = "{exception.entity.post.content.length}") String content) {
    }

    public record UpdatePostRequest(@NotNull(message = "{exception.entity.post.title.null}") @Length(min = 1, message = "{exception.entity.post.title.length}") String title,
                                    @NotNull(message = "{exception.entity.post.content.null}") @Length(min = 1, message = "{exception.entity.post.content.length}") String content) {
    }

    public record SinglePostResponse(@NotNull(message = "{exception.entity.post.id}") Long postId,
                                     @NotNull(message = "{exception.entity.post.title.null}") @Length(min = 1, message ="{exception.entity.post.title.length}") String title,
                                     @NotNull(message = "{exception.entity.post.content.null}") @Length(min = 1, message = "{exception.entity.post.content.length}") String content,
                                     @NotNull(message = "{exception.entity.user.null}") UserDto.SingleUserSimpleResponse user) {

        public static SinglePostResponse toResponse(Post post) {
            return new SinglePostResponse(post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    UserDto.SingleUserSimpleResponse.toResponse(post.getUser()));
        }
    }
}
