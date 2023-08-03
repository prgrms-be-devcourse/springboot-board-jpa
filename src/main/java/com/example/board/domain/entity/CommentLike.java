package com.example.board.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "comment_likes")
@Entity
@DiscriminatorValue("C")
public class CommentLike extends Like {
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
