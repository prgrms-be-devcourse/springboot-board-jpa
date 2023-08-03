package programmers.jpaBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programmers.jpaBoard.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
