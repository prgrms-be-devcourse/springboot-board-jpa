package com.prgrms.hyuk.domain.user;

import com.prgrms.hyuk.domain.post.Post;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class Posts {

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public void remove(Post post) {
        posts.remove(post);
    }

    public void add(Post post) {
        posts.add(post);
    }

    public List<Post> getAllPost() {
        return Collections.unmodifiableList(posts);
    }
}
