package com.poogle.board.model.user;

import com.poogle.board.model.BaseTimeEntity;
import com.poogle.board.model.post.Post;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Getter
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "hobby")
    private String hobby;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    protected User() {
    }

    private User(String name, int age, String hobby) {
        checkArgument(isNotEmpty(name), "Name must be provided.");
        checkArgument(
                hobby.length() <= 100,
                "Hobby length must be under 100 characters."
        );
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static User of(String name, int age, String hobby) {
        return new User(name, age, hobby);
    }

    public void update(String name, int age, String hobby) {
        checkArgument(isNotEmpty(name), "Name must be provided.");
        checkArgument(
                hobby.length() <= 100,
                "Hobby length must be under 100 characters."
        );
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void addPost(Post post) {
        post.setUser(this);
    }

    public List<Post> getPosts() {
        return posts;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("name", name)
                .append("age", age)
                .append("hobby", hobby)
                .toString();
    }
}
