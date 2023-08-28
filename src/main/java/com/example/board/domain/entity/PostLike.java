package com.example.board.domain.entity;

import com.example.board.contant.LikeStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_likes")
@Entity
@DiscriminatorValue("P")
public class PostLike extends Like {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public PostLike(Long id, LikeStatus likeStatus, User user, Post post) {
        super(id, likeStatus, user);
        this.post = post;
    }
}
