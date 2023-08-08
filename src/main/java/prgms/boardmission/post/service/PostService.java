package prgms.boardmission.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prgms.boardmission.domain.Post;
import prgms.boardmission.post.PostConverter;
import prgms.boardmission.post.dto.PostDto;
import prgms.boardmission.post.dto.PostUpdateDto;
import prgms.boardmission.post.exception.NotFoundPostException;
import prgms.boardmission.post.repository.PostRepository;

import java.time.LocalDateTime;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public long save(PostDto.Request request) {
        Post newPost = PostConverter.convertToPost(request);
        postRepository.save(newPost);

        return newPost.getId();
    }

    @Transactional(readOnly = true)
    public Page<PostDto.Response> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostConverter::convertToPostDto);
    }

    @Transactional(readOnly = true)
    public PostDto.Response findById(long postId) {
        return postRepository.findById(postId)
                .map(PostConverter::convertToPostDto)
                .orElseThrow(() -> new NotFoundPostException());
    }

    public PostUpdateDto.Response updatePost(Long postId, PostUpdateDto.Request request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException());

        String editTitle = request.title();
        String editContent = request.content();
        post.updatePost(editTitle, editContent);

        return new PostUpdateDto.Response(post.getId(), LocalDateTime.now());
    }
}
