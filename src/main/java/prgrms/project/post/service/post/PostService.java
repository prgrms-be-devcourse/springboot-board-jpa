package prgrms.project.post.service.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prgrms.project.post.repository.PostRepository;
import prgrms.project.post.service.DefaultPage;
import prgrms.project.post.util.mapper.PostMapper;

import java.util.NoSuchElementException;

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
        var retrievedPost = postRepository.findById(postId).orElseThrow(
            () -> new NoSuchElementException(
                format("id{0} 의 게시글을 찾을 수 없습니다.", postId)
            )
        );

        return postMapper.toDto(retrievedPost);
    }


    @Transactional(readOnly = true)
    public DefaultPage<PostDto> searchAll(Pageable pageable) {
        return DefaultPage.of(postRepository.findPostsAll(pageable).map(postMapper::toDto));
    }

    @Transactional
    public Long updatePost(Long postId, PostDto postDto) {
        var retrievedPost = postRepository.findById(postId).orElseThrow(
            () -> new NoSuchElementException(
                format("id{0} 의 게시글을 찾을 수 없습니다.", postId)
            )
        );

        return retrievedPost.updateTitleAndContent(postDto.title(), postDto.content()).getId();
    }

    @Transactional
    public void deleteById(Long postId) {
        postRepository.deleteById(postId);
    }
}
