package com.prgrms.be.app.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Max(value = 20)
    private String title;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    public Post(Long id, String title, String content, User createdBy) { // 이걸 두는게 맞나??
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
    }

    public Post(String title, String content, User createdBy) {
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
    }

    public Post changePost(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }

    public void setCreatedBy(User createdBy) {
        if (Objects.nonNull(this.createdBy)) {
            this.createdBy.getPosts().remove(this);
        }

        this.createdBy = createdBy;
        this.createdBy.getPosts().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) && Objects.equals(title, post.title) && Objects.equals(content, post.content) && Objects.equals(createdBy, post.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, createdBy);
    }
}
