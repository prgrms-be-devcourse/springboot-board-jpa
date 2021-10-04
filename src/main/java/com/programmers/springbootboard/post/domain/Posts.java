package com.programmers.springbootboard.post.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Posts {
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        posts.add(post);
    }

    public boolean ownPost(Post post) {
        if (posts.contains(post)) {
            return true;
        }
        return false;
    }

    public void deletePost(Post post) {
        posts.remove(post);
    }

    public List<Post> getPosts() {
        return posts;
    }
}
