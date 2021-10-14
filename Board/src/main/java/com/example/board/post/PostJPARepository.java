package com.example.board.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostJPARepository extends JpaRepository<Post, Integer> {
    Post deleteById(Long id);

    Optional<Post> findById(Long id);
}
