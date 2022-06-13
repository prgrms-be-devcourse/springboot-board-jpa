package com.su.gesipan.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.su.gesipan.post.PostDtoMapper.toPostResult;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    /**** Query ****/
    public Page<PostDto.Result> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostDtoMapper::toPostResult);
    }

    public PostDto.Result findById(Long id) {
        var post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("포스트를 찾을 수 없습니다."));
        return toPostResult(post);
    }

    /**** Command ****/

    @Transactional
    public void editPost(Long postId, PostDto.Update dto) {
        var post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("포스트를 찾을 수 없습니다."));
        post.editContent(dto.getContent());
        post.editTitle(dto.getTitle());
    }
}
