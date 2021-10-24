package kdt.cse0518.board.post.factory;

import kdt.cse0518.board.post.entity.Post;
import kdt.cse0518.board.post.repository.PostRepository;
import kdt.cse0518.board.user.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostFactory {

    private final PostRepository repository;

    public PostFactory(final PostRepository repository) {
        this.repository = repository;
    }

    public Post createPost(final String title, final String content, final User user) {
        final Post newPost = Post.builder()
                .title(title)
                .content(content)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
        newPost.setUser(user);
        return repository.save(newPost);
    }
}
