package spring.jpa.board.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.jpa.board.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
