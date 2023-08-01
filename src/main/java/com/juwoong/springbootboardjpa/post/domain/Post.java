package com.juwoong.springbootboardjpa.post.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.juwoong.springbootboardjpa.common.BaseEntity;
import com.juwoong.springbootboardjpa.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POSTS")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "member_id")
    private User user;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content;

    public void update (String title, String content){
        this.title =title;
        this.content =content;
    }

}
