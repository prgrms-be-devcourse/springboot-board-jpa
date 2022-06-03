package com.programmers.springbootboardjpa.service;

import com.programmers.springbootboardjpa.domain.post.Post;
import com.programmers.springbootboardjpa.domain.user.User;
import com.programmers.springbootboardjpa.dto.post.request.PostCreationRequest;
import com.programmers.springbootboardjpa.dto.post.request.PostUpdateRequest;
import com.programmers.springbootboardjpa.dto.post.response.PostResponse;
import com.programmers.springbootboardjpa.exception.NoSuchPostIdException;
import com.programmers.springbootboardjpa.exception.NoSuchUserIdException;
import com.programmers.springbootboardjpa.repository.PostRepository;
import com.programmers.springbootboardjpa.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    /**
     * 게시물 생성
     * @param request
     * @return 생성된 post id
     */
    @Transactional
    public Long savePost(PostCreationRequest request) {
        Post post = request.toEntity();
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NoSuchUserIdException());

        post.setUser(user);

        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    /**
     * post id로 게시물 조회
     * @param id
     * @return PostResponse
     */
    @Transactional
    public PostResponse findPostById(Long id) {
        Post post = postRepository.findByIdWithUser(id)
                .orElseThrow(() -> new NoSuchPostIdException());

        return post.toPostResponse();
    }

    /**
     * 모든 게시물 조회
     * @param pageable
     * @return Page<PostResponse>
     */
    @Transactional
    public Page<PostResponse> findAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAllFetchJoinWithPaging(pageable);
        return posts.map(Post::toPostResponse);
    }

    /**
     * post id를 통해 게시물 업데이트
     * title이 null이 아닌 경우에만 title 업데이트
     * content가 null이 아닌 경우에만 content 업데이트
     * @param id
     * @param request
     * @return 업데이트된 post id
     */
    @Transactional
    public Long updatePost(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchPostIdException());

        request.getTitle().ifPresent(title ->
                post.updateTitle(title)
        );

        request.getContent().ifPresent(content ->
                post.updateContent(content)
        );

        return post.getId();
    }

}
