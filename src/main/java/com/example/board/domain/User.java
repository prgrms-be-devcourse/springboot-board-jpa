package com.example.board.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private User(int id){
        this.id = id;
    }

    public static User getMockUser(int id){
        return new User(id);
    }

    @Column(name = "name", nullable = false, length = 20, unique = true)
    private String name;

    private int age;

    @Enumerated(EnumType.STRING)
    private Hobby hobby;

    @OneToMany(mappedBy = "author", cascade = CascadeType.MERGE)
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post){
        posts.add(post);
    }
}
