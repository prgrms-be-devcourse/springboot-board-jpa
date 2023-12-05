package com.prgrms.dev.springbootboardjpa.domain.post;

import com.prgrms.dev.springbootboardjpa.domain.BaseEntity;
import com.prgrms.dev.springbootboardjpa.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posts") // 넵
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Lob // 사용이 필요한지
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }

        this.user = user;
        user.getPosts().add(this);
    }

    @Builder
    public Post(String title, String content) {
        validateNull(title);
        validateNull(content);
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        validateNull(title);
        validateNull(content);
        this.title = title;
        this.content = content;
    } //service

    private void validateNull(String input) {
        if(input.isEmpty() || input.isBlank()) throw new NullPointerException("NPE");
    }

}
