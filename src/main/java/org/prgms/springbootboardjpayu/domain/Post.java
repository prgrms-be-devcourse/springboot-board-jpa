package org.prgms.springbootboardjpayu.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 30)
    @NotBlank
    @Length(min = 1, max = 30)
    private String title;

    @Column(name = "content")
    @Lob
    private String content;

    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

    @Builder
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        setUser(user);
        this.setCratedAt(LocalDateTime.now());
        this.setCreatedBy(user.getName());
    }

    public void setUser(User user) {
        Objects.requireNonNull(user);
        this.user = user;
        user.getPosts().add(this);
    }

    public void editPostTitleContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
