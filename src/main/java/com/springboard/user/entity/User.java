package com.springboard.user.entity;

import com.springboard.common.entity.BaseEntity;
import com.springboard.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Column(name = "name", nullable = false, length = 16)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "hobby", length = 32)
    private String hobby;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public User(String name, Integer age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void addPost(Post post) {
        post.setUser(this);
    }
}
