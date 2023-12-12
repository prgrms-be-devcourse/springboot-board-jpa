package jehs.springbootboardjpa.repository;

import jehs.springbootboardjpa.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.user where p.id = :id")
    Optional<Post> findByIdWithUser(Long id);

    @Query("select p from Post p join fetch p.user")
    Page<Post> findAllWithUser(Pageable pageable);

    @Query("select p from Post p join fetch p.user where p.id > :cursorId")
    Slice<Post> findAllWithUserByCursor(Long cursorId, Pageable pageable);
}
