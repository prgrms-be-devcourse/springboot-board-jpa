package com.ray.springbootboard.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.util.StringUtils;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String title;

    @Lob
    @NotBlank
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedBy
    private String createdBy;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void allocateWriter(User user) {
        this.user = user;
    }

    public void update(String title, String content) {
        validate(title);
        validate(content);
        this.title = test(this.title, title);
        this.content = test(this.content, content);
    }

    private void validate(String target) {
        if (!StringUtils.hasText(target)) {
            throw new IllegalArgumentException("빈 값일 수 없습니다");
        }
    }

    public String test(String origin, String target) {
        if (origin.equals(target)) {
            return origin;
        }

        return target;
    }
}
