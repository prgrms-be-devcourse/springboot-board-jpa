package devcource.hihi.boardjpa.repository;

import devcource.hihi.boardjpa.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitle(String title);
    @Query("SELECT p FROM Post p WHERE p.id > :cursor ORDER BY p.id ASC LIMIT :limit")
    List<Post> findByCursor(@Param("cursor") Long cursor, @Param("limit") int limit);

}
