package prgms.boardmission.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prgms.boardmission.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
