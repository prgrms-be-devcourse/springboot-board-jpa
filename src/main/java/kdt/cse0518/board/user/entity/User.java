package kdt.cse0518.board.user.entity;

import kdt.cse0518.board.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
@Builder
@Getter
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "hobby")
    private String hobby;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "modified_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    protected User() {
    }

    public void addPost(final Post post) {
        if (Objects.isNull(this.posts)) {
            this.posts = new ArrayList<>();
        }

        this.posts.add(post);
    }

    public User update(final String name, final int age, final String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        return this;
    }
}