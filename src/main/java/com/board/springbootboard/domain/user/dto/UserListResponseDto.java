package com.board.springbootboard.domain.user.dto;

import com.board.springbootboard.domain.posts.PostsEntity;
import com.board.springbootboard.domain.user.UserEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserListResponseDto {
        private Long id;
        private String name;
        private String nickName;
        private LocalDateTime modifiedDate;

        public UserListResponseDto(UserEntity entity) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.nickName = entity.getNickName();
            this.modifiedDate = entity.getModifiedDate();
        }
    }


