package kdt.prgrms.devrun.post.repository;

import kdt.prgrms.devrun.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 페이징 조회
    Page<Post> findAll(Pageable pageable);


}
