package com.example.jpaboard.service.post;

import com.example.jpaboard.service.post.dto.PostResponse;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PostService {
    List<PostResponse> findAll(Pageable pageable);

    PostResponse findById(Long id);

    void save(Long userId, String title, String content);

    PostResponse update(Long id, String title, String content);
}
