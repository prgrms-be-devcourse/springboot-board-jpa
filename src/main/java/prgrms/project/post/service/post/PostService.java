package prgrms.project.post.service.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prgrms.project.post.controller.response.PageResponse;
import prgrms.project.post.domain.post.Post;
import prgrms.project.post.repository.PostRepository;
import prgrms.project.post.util.exception.EntityNotFoundException;
import prgrms.project.post.util.mapper.PostMapper;

import static java.text.MessageFormat.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    @Transactional
    public Long uploadPost(PostDto postDto) {
        var post = postMapper.toEntity(postDto);

        return postRepository.save(post).getId();
    }

    @Transactional(readOnly = true)
    public PostDto searchById(Long postId) {
        var retrievedPost = findPost(postId);

        return postMapper.toDto(retrievedPost);
    }


    @Transactional(readOnly = true)
    public PageResponse<PostDto> searchAll(Pageable pageable) {
        return PageResponse.of(postRepository.findPostsAll(pageable).map(postMapper::toDto));
    }

    @Transactional
    public Long updatePost(Long postId, PostDto postDto) {
        var retrievedPost = findPost(postId);

        return retrievedPost.updateTitleAndContent(postDto.title(), postDto.content()).getId();
    }

    @Transactional
    public void deleteById(Long postId) {
        var retrievedPost = findPost(postId);

        postRepository.delete(retrievedPost);
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(
            () -> new EntityNotFoundException(
                format("게시글을 찾을 수 없습니다. (id: {0})", postId)
            )
        );
    }
}
