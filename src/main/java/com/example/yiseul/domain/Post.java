package com.example.yiseul.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    private Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Post createPost(Member member, String title, String content){
        Post post = new Post(title, content);
        post.changeMember(member);

        return post;
    }

    public void changeMember(Member member){
        if(Objects.nonNull(this.member)) {
            this.member.getPosts().remove(this);
        }

        this.member = member;
        member.addPost(this);
    }

    public void updatePost(String updateTitle, String updateContent) {
        changeTitle(updateTitle);
        changeContent(updateContent);
    }

    private String validateUpdateString(String update, String origin) {
        if (update == null) {

            return origin;
        }
        return update;
    }

}
