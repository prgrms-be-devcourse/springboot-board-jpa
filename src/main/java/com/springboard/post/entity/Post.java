package com.springboard.post.entity;

import com.springboard.common.entity.BaseEntity;
import com.springboard.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "post")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE post SET is_deleted = true WHERE id = ?")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity {
    @Column(name = "title", nullable = false, length = 64)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void setUser(User user) {
        if (Objects.nonNull(this.user)){
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }
}
