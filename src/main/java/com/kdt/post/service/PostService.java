package com.kdt.post.service;

import com.kdt.domain.post.PostRepository;
import com.kdt.post.dto.PostSaveDto;
import com.kdt.post.dto.PostViewDto;
import java.text.MessageFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final PostConvertor postConvertor;

    @Transactional
    public Long save(PostSaveDto postSaveDto) {
        return postRepository.save(postConvertor.convertPostSaveDtoToPost(postSaveDto)).getId();
    }

    public Page<PostViewDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(postConvertor::convertPostToPostViewDto);
    }

    public PostViewDto findOne(Long id) {
        return postRepository.findById(id)
                .map(postConvertor::convertPostToPostViewDto)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("not found id : {0}", id)));
    }

    @Transactional
    public Long update(Long postId, PostSaveDto postSaveDto) {
        return postRepository.findById(postId)
                .map(post -> postConvertor.convertPostSaveDtoToPost(postSaveDto))
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("not found postId : {0}", postSaveDto.getId())))
                .getId();
    }
}
