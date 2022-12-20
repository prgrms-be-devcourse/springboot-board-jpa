package com.prgrms.java;

import com.prgrms.dto.CreatePostRequest;
import com.prgrms.java.domain.Post;
import com.prgrms.java.dto.GetPostDetailsResponse;
import com.prgrms.java.dto.GetPostsResponse;
import com.prgrms.java.exception.ResourceNotFountException;
import com.prgrms.java.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;


@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
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
    public void addPost(CreatePostRequest createPostRequest) {
        Post postRequestInfo = createPostRequest.toEntity();
        Post savedPost = postRepository.save(postRequestInfo);
    }
}
