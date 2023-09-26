package devcource.hihi.boardjpa.domain;

import devcource.hihi.boardjpa.dto.user.CreateRequestDto;
import devcource.hihi.boardjpa.dto.user.ResponseUserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity{

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer age;

    private String hobby;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @Builder
    public User(String name, Integer age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeAge(Integer age) {
        this.age = age;
    }

    public void changeHobby(String hobby) {
        this.hobby = hobby;
    }



    public void addPost(Post post) {
        this.getPostList().add(post);
    }

    public void removePost(Post post) {
        this.getPostList().remove(post);
    }

}
