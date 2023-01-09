package com.devcourse.springbootboardjpa.post_like.domain;

import com.devcourse.springbootboardjpa.config.BaseEntity;
import com.devcourse.springbootboardjpa.post.domain.Post;
import com.devcourse.springbootboardjpa.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post_like")
public class PostLike extends BaseEntity {

    @EmbeddedId
    private PostLikeId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("postId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void setUser(User user) {
        if (this.user != null) {
            this.user.getPostLikes().remove(this);
        }
        this.user = user;
        user.getPostLikes().add(this);
    }

    public void setPost(Post post) {
        if (this.post!= null) {
            this.post.getPostLikes().remove(this);
        }
        this.post = post;
        post.getPostLikes().add(this);
    }

}