package devcource.hihi.boardjpa.repository;

import devcource.hihi.boardjpa.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select * from Post p limit :size", nativeQuery = true)
    List<Post> findFirstPage(int size);

    @Query(value = "select * from Post p where p.id >: cursorId limit :size", nativeQuery = true)
    List<Post> findByIdLessThanOrderByIdDesc(Long cursorId, int size);

    Boolean existsByIdLessThan(Long id);
}
