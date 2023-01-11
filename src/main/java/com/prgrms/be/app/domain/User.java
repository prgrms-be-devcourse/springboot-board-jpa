package com.prgrms.be.app.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 2, max = 17)
    private String name;

    @NotNull
    @Min(value = 1)
    @Max(value = 120)
    private Integer age;

    private String hobby;

    @OneToMany(mappedBy = "createdBy")
    private List<Post> posts = new ArrayList<>();

    public User(String name, Integer age, String hobby) {
        checkArgument(!name.isBlank(), "이름은 공백만으로 이루어질 수 없습니다.", name);

        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void addPost(Post post) {
        post.setCreatedBy(this);
    }
}
