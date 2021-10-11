package spring.jpa.board.post.post;

import spring.jpa.board.post.repository.PostRepository;

public class PostSerivce {
    private final PostRepository postRepository;

    public PostSerivce(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
