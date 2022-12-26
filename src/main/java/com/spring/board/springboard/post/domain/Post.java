package com.spring.board.springboard.post.domain;

import com.spring.board.springboard.user.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "title", nullable = false, length = 100)
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @Column(name = "content", nullable = false)
    @Lob
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Column(name = "create_at", nullable = false)
    @NotNull
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @NotNull(message = "작성자가 없을 수 없습니다.")
    private Member member;

    protected Post() {
    }

    public Post(String title, String content, LocalDateTime createdAt, Member member){
        this.createdAt = createdAt;
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Member getMember() {
        return member;
    }

    public void changeTitle(String changeTitle) {
        validate(changeTitle);
        this.title = changeTitle;
    }

    public void changeContent(String changeContent) {
        validate(changeContent);
        this.content = changeContent;
    }

    public Integer getMemberId(){
        return this.member.getId();
    }

    public void changeMember(Member member) {
        if(Objects.nonNull(this.member)){
            this.member.
                    getPostList()
                    .remove(this);
        }
        this.member = member;
        member.getPostList().add(this);
    }
}
