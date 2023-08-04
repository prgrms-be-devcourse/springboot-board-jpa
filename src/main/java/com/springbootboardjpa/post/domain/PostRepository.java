package com.springbootboardjpa.post.domain;

import com.springbootboardjpa.common.NoSuchEntityException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long id);

    default Post getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new NoSuchEntityException("해당 아이디의 게시물이 존재하지 않습니다."));
    }
}
