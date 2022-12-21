package com.spring.board.springboard.post.domain;

import com.spring.board.springboard.user.domain.User;
import com.spring.board.springboard.user.exception.AuthenticationException;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "post")
public class Post {

    private static final Integer NOTHING = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false)
    @Lob
    private String content;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    public Post() {
    }

    public Post(String title, String content, LocalDateTime createdAt, Member member){
        validate(title);
        validate(content);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ||
                getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) &&
                Objects.equals(title, post.title) &&
                Objects.equals(content, post.content) &&
                Objects.equals(createdAt, post.createdAt) &&
                Objects.equals(member, post.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, createdAt, member);
    }

    private void validate(String input) {
        if (Objects.equals(input.length(), NOTHING)
                && input.isEmpty()) {
            throw new IllegalArgumentException("빈 값일 수 없습니다. 반드시 입력해야합니다.");
        }
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
