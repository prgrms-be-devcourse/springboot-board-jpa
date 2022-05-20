package org.programmers.springbootboardjpa.service.post;

import lombok.RequiredArgsConstructor;
import org.programmers.springbootboardjpa.domain.Post;
import org.programmers.springbootboardjpa.domain.user.User;
import org.programmers.springbootboardjpa.repository.user.PostRepository;
import org.programmers.springbootboardjpa.repository.user.UserRepository;
import org.programmers.springbootboardjpa.service.exception.NotFoundException;
import org.programmers.springbootboardjpa.web.dto.post.PostCreateForm;
import org.programmers.springbootboardjpa.web.dto.post.PostUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Long writePost(PostCreateForm postCreateForm) {
        //TODO: 세션 정보를 이용하여 사용자 특정
        User writer = userRepository.getById(postCreateForm.userId());
        return postRepository.save(postCreateForm.convertToPost(writer)).getPostId();
    }

    @Override
    public Post findPostWithPostId(Long postId) {
        return postRepository.findFetchByPostId(postId).orElseThrow(throwPostIsNotFound(postId));
    }

    private Supplier<NotFoundException> throwPostIsNotFound(Long postId) {
        return () -> new NotFoundException(postId, "Post");
    }

    @Transactional
    @Override
    public Post editPost(PostUpdateForm postUpdateForm) {
        //TODO: 포스트 작성 중에 게시자 데이터 변경을 생각해야 할까
        Post post = postRepository.findById(postUpdateForm.postId()).orElseThrow(throwPostIsNotFound(postUpdateForm.postId()));
        return postUpdateForm.applyToPost(post);
    }

    @Transactional
    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public Page<Post> findPostsWithPage(Pageable pageable) {
        return postRepository.findFetchAllBy(pageable);
    }

    @Override
    public Page<Post> findPostByUserWithPage(Long userId, Pageable pageable) {
        User writer = userRepository.getById(userId);
        return postRepository.findFetchByUser(writer, pageable);
    }
}
