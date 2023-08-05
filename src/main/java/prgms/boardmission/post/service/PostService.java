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

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public long save(PostDto postDto) {
        Post newPost = PostConverter.convertToPost(postDto);
        Post Post = postRepository.save(newPost);

        return newPost.getId();
    }

    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostConverter::convertToPostDto);
    }

    public PostDto findById(long postId) {
        return postRepository.findById(postId)
                .map(PostConverter::convertToPostDto)
                .orElseThrow(() -> new NotFoundPostException("해당 게시글을 찾을 수 없습니다."));
    }

    public Long update(Long postId, PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(postId).get();
        String editContent = postUpdateDto.content();

        post.updatePost(editContent);

        return post.getId();
    }
}
