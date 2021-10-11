package yjh.jpa.springnoticeboard.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Table(name = "user")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{

    //validation 추가 해주기
    //last modified도 만들까??
    //입력될때 한번에 입력되도록 하는 method 추가하기

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20)")
    private Long id;

    @Column(name = "name", columnDefinition = "varchar(100)", length = 30)
    private String name;

    @Column(name = "age", columnDefinition = "bigint(20)")
    private int age;

    @Column(name = "hobby", columnDefinition = "varchar(100)")
    private String hobby;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post){
        if(posts == null) posts = new ArrayList<>();
        post.setUser(this);
    }

}
