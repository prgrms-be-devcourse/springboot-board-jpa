package com.prgrms.be.app.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 1, max = 20)
    private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    public Post(String title, String content, User createdBy) {
        checkArgument(!title.isBlank(), "제목은 공백으로만 이루어질 수 없습니다.", title);
        checkArgument(!content.isBlank(), "제목은 공백으로만 이루어질 수 없습니다.", content);

        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
    }

    public Post changePost(String title, String content) {
        checkArgument(!title.isBlank() && title.length() > 0 && title.length() <= 20, "게시물 제목의 길이가 0보다 크고 20 이하가 되야 합니다.", title);
        checkArgument(!content.isBlank(), "게시물 본문 내용이 비어있으면 안됩니다.", content);

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
