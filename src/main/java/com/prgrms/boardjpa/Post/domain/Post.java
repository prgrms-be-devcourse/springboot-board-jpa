package com.prgrms.boardjpa.Post.domain;

import com.prgrms.boardjpa.Post.dto.request.PostUpdateRequest;
import com.prgrms.boardjpa.User.domain.User;
import com.prgrms.boardjpa.global.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateUser(User author) {
        this.user = author;
    }

    public void update(PostUpdateRequest updateRequest) {
        if (updateRequest.title() != null) {
            this.title = updateRequest.title();
        }

        if (updateRequest.content() != null) {
            this.content = updateRequest.content();
        }
    }
}