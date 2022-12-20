package com.example.springbootboardjpa.repoistory;

import com.example.springbootboardjpa.domian.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PostJpaRepository extends JpaRepository<Post, Integer> {
}
