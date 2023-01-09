package com.devcourse.springbootboardjpa.post_hashtag.domain;

import com.devcourse.springbootboardjpa.hashtag.domain.Hashtag;
import com.devcourse.springbootboardjpa.post.domain.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post_hashtag")
public class PostHashtag {

    @EmbeddedId
    private PostHashtagId id;

    @MapsId("postId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @MapsId("hashtagId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    public void setPost(Post post) {
        if (this.post != null) {
            this.post.getPostHashtags().remove(this);
        }
        this.post = post;
        post.getPostHashtags().add(this);
    }

    public void setHashtag(Hashtag hashtag) {
        if (this.hashtag != null) {
            this.hashtag.getPostHashtags().remove(this);
        }
        this.hashtag = hashtag;
        hashtag.getPostHashtags().add(this);
    }
}
