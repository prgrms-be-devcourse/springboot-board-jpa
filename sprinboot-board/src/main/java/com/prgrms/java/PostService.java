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
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public GetPostsResponse getPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);

        return new GetPostsResponse(
                postPage.stream()
                        .map(GetPostDetailsResponse::from)
                        .toList()
        );
    }

    public GetPostDetailsResponse getPostDetail(long postId) {
        Post savedPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFountException(MessageFormat.format("Can not find Post. Please check post id. [Post ID]: {0}", postId)));

        return GetPostDetailsResponse.from(savedPost);
    }

    @Transactional
    public long createPost(CreatePostRequest createPostRequest) {
        User user = userRepository.findById(createPostRequest.userId())
                .orElseThrow(() -> new ResourceNotFountException(MessageFormat.format("Can not find User. Please check user id. [User ID]: {0}", createPostRequest.userId())));

        Post post = postRepository.save(createPostRequest.toEntity(user));
        return post.getId();
    }

    @Transactional
    public void modifyPost(long id, ModifyPostRequest modifyPostRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFountException(MessageFormat.format("Can not find Post. Please check post id. [Post ID]: {0}", id)));

        post.editPost(modifyPostRequest.title(), modifyPostRequest.content());
    }
}
