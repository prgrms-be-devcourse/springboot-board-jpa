package com.kdt.springbootboard.domain.user;


import com.kdt.springbootboard.common.BaseEntity;
import com.kdt.springbootboard.domain.post.Post;
import com.kdt.springbootboard.domain.user.vo.Age;
import com.kdt.springbootboard.domain.user.vo.Email;
import com.kdt.springbootboard.domain.user.vo.Hobby;
import com.kdt.springbootboard.domain.user.vo.Name;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    @Embedded
    private Name name;
    @Embedded
    private Email email;
    @Embedded
    private Age age;
    @Embedded
    private Hobby hobby;
    
    // Note : 유저 삭제시 게시글까지 삭제
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    public void update(Name name, Email email, Age age, Hobby hobby) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.hobby = hobby;
    }

    public void addPost(Post post) {
        post.setUser(this);
    }
}
