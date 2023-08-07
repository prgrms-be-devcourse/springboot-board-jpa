package devcource.hihi.boardjpa.repository;

import devcource.hihi.boardjpa.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    String CursorQuery = "SELECT * FROM post WHERE post_id > :cursor ORDER BY post_id ASC LIMIT :pageSize";
    @Query(value = CursorQuery, nativeQuery = true)
    List<Post> findByCursor(@Param("cursor") Long cursor, @Param("pageSize") int pageSize);

}
