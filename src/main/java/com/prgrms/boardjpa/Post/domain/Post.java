package com.prgrms.boardjpa.Post.domain;

import com.prgrms.boardjpa.Post.dto.request.PostUpdateRequest;
import com.prgrms.boardjpa.User.domain.User;
import com.prgrms.boardjpa.global.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
        user.addPost(this);
    }

    public void update(PostUpdateRequest updateRequest) {
        this.title = updateRequest.title();
        this.content = updateRequest.content();
    }
}