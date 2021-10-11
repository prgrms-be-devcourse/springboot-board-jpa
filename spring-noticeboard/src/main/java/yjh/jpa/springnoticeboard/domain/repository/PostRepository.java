package yjh.jpa.springnoticeboard.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yjh.jpa.springnoticeboard.domain.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
