package com.example.jpaboard.service.post;

import com.example.jpaboard.service.dto.post.PostResponse;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PostService {
    List<PostResponse> findAll(Pageable pageable);

    PostResponse findById(Long id);

    void save(Long userId, String title, String content);

    PostResponse update(Long id, Long userId, String title, String content);
}
