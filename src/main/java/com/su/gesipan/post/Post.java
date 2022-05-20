package com.su.gesipan.post;

import com.su.gesipan.common.audit.BaseEntity;
import com.su.gesipan.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static com.google.common.base.Preconditions.checkArgument;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = PROTECTED)
public class Post extends BaseEntity {

    @Id @GeneratedValue
    private Long id;
    @NotBlank
    private String title;
    @Lob
    private String content;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    // INFO: Post 에서 USER 와 직접적으로 관계맺는 곳은 생성자에서밖에 없다.
    private Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }
    private Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Post of(String title, String content, User user){
        return new Post(title, content, user);
    }
    public static Post of(String title, String content){
        return new Post(title, content);
    }

    public void editTitle(String title){
        checkArgument(title.length() < 100, "타이틀 길이는 100자를 넘어갈 수 없습니다.");
        this.title = title;
    }
    public void editContent(String content){
        checkArgument(content.length() < 1500, "본문 길이는 1500자를 넘어갈 수 없습니다.");
        this.content = content;
    }

    // WARN: 직접 호출하지 않는다! 유저를 통해서 호출해서 사용해야 한다.
    public void relationshipWith(User user){
        this.user = user;
    }

    @PrePersist
    public void prePersist(){
        this.setCreatedBy(user.getId());
        this.setUpdatedBy(user.getId());
    }
}
