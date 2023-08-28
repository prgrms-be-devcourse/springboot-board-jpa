package com.example.board.domain.entity;


import com.example.board.contant.LikeStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "likes")
@Inheritance(strategy = InheritanceType.JOINED)
//조인 테이블 전략 상속
@DiscriminatorColumn
public abstract class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @Column(name = "like_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private LikeStatus likeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_id")
    private User user;

    public Like(Long id, LikeStatus likeStatus, User user) {
        this.id = id;
        this.likeStatus = likeStatus;
        this.user = user;
    }
}
