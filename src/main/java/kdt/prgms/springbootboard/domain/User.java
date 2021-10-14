package kdt.prgms.springbootboard.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "User")
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", nullable = false, length = 50, unique = true)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Embedded
    private Hobby hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    protected User() {
    }

    private User(String name, int age, Hobby hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static User createUser(String name, int age, Hobby hobby) {
        return new User(name, age, hobby);
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void removePost(Post post) {
        this.posts.remove(post);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Hobby getHobby() {
        return hobby;
    }
}
