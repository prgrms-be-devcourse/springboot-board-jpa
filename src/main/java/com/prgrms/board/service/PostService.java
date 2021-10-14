package com.prgrms.board.service;

import com.prgrms.board.domain.Post;
import com.prgrms.board.domain.User;
import com.prgrms.board.dto.post.PostCreateRequest;
import com.prgrms.board.dto.post.PostFindResponse;
import com.prgrms.board.dto.post.PostModifyRequest;
import com.prgrms.board.error.exception.NotFoundException;
import com.prgrms.board.repository.PostRepository;
import com.prgrms.board.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long createPost(PostCreateRequest postCreateRequest){
        User foundUser = getUser(postCreateRequest.userId());

        Post post = Post.builder()
                .title(postCreateRequest.title())
                .content(postCreateRequest.content())
                .user(foundUser)
                .build();

        return postRepository.save(post).getId();
    }

    @Transactional
    public Long modifyPost(PostModifyRequest postModifyRequest){
        Post post = getPost(postModifyRequest.getId());
        post.changeInfo(postModifyRequest.getTitle(), postModifyRequest.getContent());

        return post.getId();
    }

    @Transactional(readOnly = true)
    public PostFindResponse findPost(final Long id) {
        Post test = getPost(id);
        return new PostFindResponse(test);
    }

    @Transactional(readOnly = true)
    public Page<PostFindResponse> findAllPostByUserId(Pageable pageable, final Long userId){
        User user = getUser(userId);
        return postRepository.findAllByUser(pageable, user)
                .map(PostFindResponse::new);
    }

    @Transactional
    public Long removePost(final Long id) {
        Post post = getPost(id);
        postRepository.delete(post);

        return post.getId();
    }

    private Post getPost(final Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));
    }

    private User getUser(final Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));
    }

}
