package com.prgrms.boardjpa.domain;

import com.prgrms.boardjpa.user.dto.AuthorDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity{
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    private int age = 0;

    @Column(length = 30)
    private String hobby;

    public User(){}

    public User(String name, int age, String hobby){
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post){
        post.setAuthor(this);
    }

    public AuthorDto toAuthorDto(){
        return AuthorDto.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
