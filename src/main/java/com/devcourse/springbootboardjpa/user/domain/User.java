package com.devcourse.springbootboardjpa.user.domain;

import com.devcourse.springbootboardjpa.comment.domain.Comment;
import com.devcourse.springbootboardjpa.config.BaseEntity;
import com.devcourse.springbootboardjpa.post.domain.Post;
import com.devcourse.springbootboardjpa.post_like.domain.PostLike;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "user")
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Length(min = 5, max = 15)
    @NotBlank
    @Column(name = "login_id")
    private String loginId;

    @Length(min = 10, max = 30)
    @NotBlank
    @Column(name = "password")
    private String password;

    @Length(min = 1, max = 20)
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Range(min = 1, max = 150)
    @Column(name = "age")
    private Integer age;

    @Column(name = "hobby")
    private String hobby;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PostLike> postLikes = new ArrayList<>();
}
