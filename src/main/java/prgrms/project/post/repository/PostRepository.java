package prgrms.project.post.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prgrms.project.post.domain.post.Post;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    @Query("select p from Post p join fetch p.user where p.id = :id")
    Optional<Post> findById(Long id);

    @Query("select p from Post p join fetch p.user")
    Slice<Post> findPostsAll(Pageable pageable);
}
