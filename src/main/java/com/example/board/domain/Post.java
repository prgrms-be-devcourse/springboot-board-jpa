package com.example.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name="post")

public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(name = "title",nullable = false)
    private String title;

    @Lob
    private String content;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;

    public void setUser(User user) {
        if(Objects.nonNull(this.user)){
            user.getPost().remove(this);
        }
        this.user = user;
        user.getPost().add(this);
        this.setCreatedBy(user.getName());
    }

}
