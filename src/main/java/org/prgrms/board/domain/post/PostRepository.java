package org.prgrms.board.domain.post;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

	@Query("select count(p) from Post p")
	long count();

	@Query("select p from Post p join fetch p.writer where p.id = :id")
	Optional<Post> findById(Long id);
}