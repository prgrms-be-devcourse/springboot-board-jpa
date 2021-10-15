package com.programmers.board.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "post")
@Getter
@Setter
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false, length = 200)
    private String content;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // User : Post = 1 : N 관계
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void setUser(User user){
        if(Objects.nonNull(this.user)){
            user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }
}
