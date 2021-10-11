package com.programmers.springbootboard.post.domain;

import com.programmers.springbootboard.exception.ErrorMessage;
import com.programmers.springbootboard.exception.error.NotFoundException;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Embeddable
public class Posts {
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        posts.add(post);
    }

    public boolean ownPost(Post post) {
        return posts.contains(post);
    }

    public void deletePost(Post post) {
        posts.remove(post);
    }

    public Post findPostById(Long id) {
        return posts.stream()
                .filter(post -> Objects.equals(post.getId(), id))
                .findFirst()
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_POST);
                });
    }
}
