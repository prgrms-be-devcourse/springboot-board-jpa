package spring.jpa.board.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.jpa.board.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findPostsByUserId(Long id);
}
