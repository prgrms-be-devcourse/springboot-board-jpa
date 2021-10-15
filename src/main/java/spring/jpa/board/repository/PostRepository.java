package spring.jpa.board.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.jpa.board.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

  @Query("SELECT p FROM Post AS p JOIN User AS u ON p.user.id = u.id")
  List<Post> findByUserId(Long id);
}
