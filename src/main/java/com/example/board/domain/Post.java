package com.example.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "posts", indexes = @Index(name = "idx_created_at", columnList = "createdAt"))
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User author;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Boolean isSameAuthorId(Long authorId) {
        return author.getId().equals(authorId);
    }
}
