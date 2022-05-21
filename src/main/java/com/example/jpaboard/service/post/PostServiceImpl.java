package com.example.jpaboard.service.post;

import com.example.jpaboard.service.dto.post.PostResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Override
    public List<PostResponse> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public PostResponse findById(Long id) {
        return null;
    }

    @Override
    public void save(Long userId, String title, String content) {

    }

    @Override
    public PostResponse update(Long id, Long userId, String title, String content) {
        return null;
    }
}
