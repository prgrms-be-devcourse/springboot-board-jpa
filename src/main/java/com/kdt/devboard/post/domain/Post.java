package com.kdt.devboard.post.domain;

import com.kdt.devboard.Common.BaseTimeEntity;
import com.kdt.devboard.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //ToDo : Mysql 사용시 identity로 변경
    private Long id;

    @NotNull
    private String title;

    @Lob
    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Builder
    public Post(Long id,String title, String content, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void changeInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }

}