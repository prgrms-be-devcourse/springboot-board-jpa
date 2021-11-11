package kdt.prgms.springbootboard.repository;

import java.util.List;
import kdt.prgms.springbootboard.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleContaining(String title);
}
