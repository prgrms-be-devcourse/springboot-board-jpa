package prgrms.board.post.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prgrms.board.global.exception.ErrorCode;
import prgrms.board.post.application.dto.request.PostSaveRequest;
import prgrms.board.post.application.dto.request.PostUpdateRequest;
import prgrms.board.post.application.dto.response.PostFindResponse;
import prgrms.board.post.application.dto.response.PostSaveResponse;
import prgrms.board.post.application.dto.response.PostUpdateResponse;
import prgrms.board.post.domain.Post;
import prgrms.board.post.domain.PostRepository;
import prgrms.board.post.exception.PostNotFoundException;
import prgrms.board.user.domain.User;
import prgrms.board.user.domain.UserRepository;
import prgrms.board.user.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(
            PostRepository postRepository,
            UserRepository userRepository
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PostSaveResponse savePost(PostSaveRequest request) {
        Post newPost = request.toEntity();

        Long userId = request.userId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        ErrorCode.USER_NOT_FOUND,
                        this.getClass().getName()
                ));
        newPost.updateUser(user);

        postRepository.save(newPost);

        return PostSaveResponse.of(newPost);
    }

    @Transactional
    public PostUpdateResponse updatePost(
            Long postId,
            PostUpdateRequest request
    ) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(
                        ErrorCode.POST_NOT_FOUND,
                        this.getClass().getName()
                ));

        String postTitle = request.title();
        String postContent = request.content();
        post.updateTitle(postTitle);
        post.updateContent(postContent);

        return PostUpdateResponse.of(post);
    }

    public PostFindResponse findByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(
                        ErrorCode.POST_NOT_FOUND,
                        this.getClass().getName()
                ));

        return PostFindResponse.of(post);
    }

    public Page<PostFindResponse> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostFindResponse::of);
    }
}
