package com.prgrms.java;

import com.prgrms.java.domain.User;
import com.prgrms.java.dto.CreatePostRequest;
import com.prgrms.java.domain.Post;
import com.prgrms.java.dto.GetPostDetailsResponse;
import com.prgrms.java.dto.GetPostsResponse;
import com.prgrms.java.dto.ModifyPostRequest;
import com.prgrms.java.exception.ResourceNotFountException;
import com.prgrms.java.repository.PostRepository;
import com.prgrms.java.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;


@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public GetPostsResponse getPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);

        return new GetPostsResponse(
                postPage.stream()
                        .map(GetPostDetailsResponse::from)
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public GetPostDetailsResponse getPostDetail(long postId) {
        Post savedPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFountException(MessageFormat.format("Can not find Post. Please check post id. [Post ID]: {0}", String.valueOf(postId))));

        return GetPostDetailsResponse.from(savedPost);
    }

    @Transactional
    public long createPost(CreatePostRequest createPostRequest) {
        User user = userRepository.findById(createPostRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFountException(MessageFormat.format("Can not find User. Please check user id. [User ID]: {0}", createPostRequest.getUserId())));

        Post post = createPostRequest.toEntity();
        post.assignUser(user);

        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional
    public void modifyPost(long id, ModifyPostRequest modifyPostRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException(MessageFormat.format("Can not find Post. Please check post id. [Post ID]: {0}", String.valueOf(id))));

        post.editPost(modifyPostRequest.getTitle(), modifyPostRequest.getContent());
    }
}
