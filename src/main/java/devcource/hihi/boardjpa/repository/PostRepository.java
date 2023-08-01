package devcource.hihi.boardjpa.repository;

import devcource.hihi.boardjpa.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitle(String title);
    @Query(value = "SELECT * FROM Post ORDER BY post_id DESC LIMIT :limit", nativeQuery = true)
    List<Post> findTopByOrderByIdDesc(@Param("limit") int limit);
    @Query(value = "SELECT * FROM Post WHERE post_id < :cursor ORDER BY post_id DESC LIMIT :limit", nativeQuery = true)
    List<Post> findByIdLessThanOrderByIdDesc(@Param("cursor") Long cursor, @Param("limit") int limit);

}
