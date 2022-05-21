package com.example.jpaboard.service.post;

import com.example.jpaboard.domain.post.Post;
import com.example.jpaboard.domain.post.PostRepository;
import com.example.jpaboard.domain.user.User;
import com.example.jpaboard.domain.user.UserRepository;
import com.example.jpaboard.exception.CustomException;
import com.example.jpaboard.service.dto.post.PostResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import static com.example.jpaboard.exception.ErrorCode.*;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PostResponse> findAll(Pageable pageable) {
        List<Post> posts = postRepository.findWithPagination(pageable);
        return posts.stream()
                    .map((post) -> PostResponse.from(post))
                    .collect(Collectors.toList());
    }

    @Override
    public PostResponse findById(Long id) {
        if (id == null) {
            throw new CustomException(INVALID_REQUEST);
        }
        Post post = postRepository.findById(id)
                                  .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        return PostResponse.from(post);
    }

    @Override
    public void save(Long userId, String title, String content) {
        if (userId == null || title == null || content == null) {
            throw new CustomException(INVALID_REQUEST);
        }

        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        postRepository.save(new Post(title, content, user));
    }

    @Override
    public PostResponse update(Long id, Long userId, String title, String content) {
        if (id == null || userId == null || title == null || content == null) {
            throw new CustomException(INVALID_REQUEST);
        }

        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Post post = postRepository.findById(id)
                                  .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        if (!post.isSameWriter(user)) {
            throw new CustomException(FORBIDDEN_USER);
        }

        post.update(title, content);

        return PostResponse.from(post);
    }
}
