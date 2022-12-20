package com.example.springbootboardjpa.repoistory;

import com.example.springbootboardjpa.domian.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Integer> {
}
