package com.devcourse.bbs.service.post;

import com.devcourse.bbs.controller.bind.PostCreateRequest;
import com.devcourse.bbs.controller.bind.PostUpdateRequest;
import com.devcourse.bbs.domain.post.Post;
import com.devcourse.bbs.domain.post.PostDTO;
import com.devcourse.bbs.domain.user.User;
import com.devcourse.bbs.repository.post.PostRepository;
import com.devcourse.bbs.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BasicPostService implements PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public PostDTO createPost(PostCreateRequest request) {
        User user = userRepository.findById(request.getUser()).orElseThrow(() -> {
            throw new IllegalArgumentException("User with given id not found.");
        });
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        postRepository.save(post);
        return post.toDTO();
    }

    @Override
    public PostDTO updatePost(PostUpdateRequest request) {
        Post post = postRepository.findById(request.getId()).orElseThrow(() -> {
            throw new IllegalArgumentException("Post with given id not found.");
        });
        post.changeTitle(request.getTitle());
        post.changeContent(request.getContent());
        return post.toDTO();
    }

    @Override
    public Optional<PostDTO> findPostById(long id) {
        return postRepository.findById(id).map(Post::toDTO);
    }

    @Override
    public List<PostDTO> findPostsByPage(int pageNum, int pageSize) {
        return postRepository.findAll(PageRequest.of(pageNum, pageSize)).map(Post::toDTO).toList();
    }
}
