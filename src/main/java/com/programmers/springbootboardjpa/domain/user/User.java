package com.programmers.springbootboardjpa.domain.user;

import com.programmers.springbootboardjpa.domain.BaseEntity;
import com.programmers.springbootboardjpa.domain.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static org.springframework.util.StringUtils.hasText;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User  extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    private Integer age;
    private String bobby;

    private User(String name) {
        checkArgument(hasText(name), "should have NAME");
        this.name = name;
    }

    public static User create(String name) {
        return new User(name);
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Post> posts = new ArrayList<>();
}
