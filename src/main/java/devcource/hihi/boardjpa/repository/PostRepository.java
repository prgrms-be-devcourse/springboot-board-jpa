package devcource.hihi.boardjpa.repository;

import devcource.hihi.boardjpa.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
