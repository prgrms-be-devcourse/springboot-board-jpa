package com.example.board.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Post")
@Table(name = "post")
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    private @Lob String content;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private User author;

    public void setUser(User user){
        if (Objects.nonNull(this.author)) {
            author.getPosts().remove(this);
        }
        this.author = user;
        author.getPosts().add(this);

    }
}
