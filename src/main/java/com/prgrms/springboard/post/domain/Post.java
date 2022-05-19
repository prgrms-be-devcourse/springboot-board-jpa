package com.prgrms.springboard.post.domain;

import java.time.LocalDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.prgrms.springboard.global.common.BaseEntity;
import com.prgrms.springboard.post.domain.vo.Content;
import com.prgrms.springboard.post.domain.vo.Title;
import com.prgrms.springboard.user.domain.User;

import lombok.Getter;

@Getter
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    protected Post() {
    }

    public Post(String createdBy, LocalDateTime createdAt, Long id, Title title, Content content, User user) {
        super(createdBy, createdAt);
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        user.addPost(this);
    }

    public static Post of(String title, String content, User user) {
        String createdBy = user.getName().getValue();
        Title postTitle = new Title(title);
        Content postContent = new Content(content);
        return new Post(createdBy, LocalDateTime.now(), null, postTitle, postContent, user);
    }

    public void changePostInformation(String title, String content) {
        this.title = new Title(title);
        this.content = new Content(content);
    }

    public boolean isNotSameUser(User user) {
        return !this.getUser().isSame(user);
    }
}
