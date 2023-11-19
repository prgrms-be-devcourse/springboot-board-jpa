package com.example.board.domain.post.repository;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    void deleteAllByMember(Member member);
}
