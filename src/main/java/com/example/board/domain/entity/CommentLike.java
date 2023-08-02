package com.example.board.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment_likes")
@Entity
@DiscriminatorValue("C")
public class CommentLike extends Like {
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
