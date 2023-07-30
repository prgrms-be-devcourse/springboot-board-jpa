package devcource.hihi.boardjpa.repository;

import devcource.hihi.boardjpa.controller.CursorResult;
import devcource.hihi.boardjpa.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByIdDesc(Pageable page);

    List<Post> findByIdLessThanOrderByIdDesc(Long id, Pageable page);

    Boolean existsByIdLessThan(Long id);

    List<Post> findByUserId(Long id);

    List<Post> findByTitle(String title);
}
