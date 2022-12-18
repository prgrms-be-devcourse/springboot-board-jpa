package kdt.springbootboardjpa.respository;

import kdt.springbootboardjpa.respository.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
