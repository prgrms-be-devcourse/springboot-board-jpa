package com.prgrms.boardapp.model;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Posts {
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public Posts() {
        this.posts = new ArrayList<>();
    }

    public void remove(Post post) {
        this.posts.remove(post);
    }

    public void add(Post post) {
        this.posts.add(post);
    }

    public int size() {
        return posts.size();
    }

    public Post get(int index) {
        return posts.get(index);
    }
}
