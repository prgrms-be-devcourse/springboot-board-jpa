package kdt.cse0518.board.post.repository;

import kdt.cse0518.board.post.entity.Post;
import kdt.cse0518.board.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUser(User user, Pageable pageable);

    Page<Post> findAll(Pageable pageable);
}
