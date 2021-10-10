package com.kdt.devboard.post.domain;

import com.kdt.devboard.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)//ToDo : Mysql 사용시 identity로 변경
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public Post(String createBy, String title, String content) {
        super(createBy);
        this.title = title;
        this.content = content;
    }

    public void changeInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }



}
