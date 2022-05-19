package com.kdt.jpaboard.domain.board.post;


import com.kdt.jpaboard.domain.board.BaseEntity;
import com.kdt.jpaboard.domain.board.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Posts extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void updateUserInfo(User user) {

        if(Objects.nonNull(this.user)) {
            this.user.addPosts(this);
        }
        this.user = user;
        user.addPosts(this);
        this.setCreatedBy(user.getName());
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
