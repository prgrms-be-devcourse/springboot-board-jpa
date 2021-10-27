package com.assignment.bulletinboard.post.service;

import com.assignment.bulletinboard.post.Post;
import com.assignment.bulletinboard.post.PostRepository;
import com.assignment.bulletinboard.post.converter.PostConverter;
import com.assignment.bulletinboard.post.dto.PostSaveDto;
import com.assignment.bulletinboard.post.dto.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostConverter postConverter;
    private final PostRepository postRepository;

    @Transactional
    public Long save(PostSaveDto postSaveDto) {
        return postRepository.save(postConverter.convertToPost(postSaveDto)).getId();
    }

    @Transactional
    public Long update(PostUpdateDto postUpdateDto) throws RuntimeException {
        return postRepository.findById(postUpdateDto.getId())
                .map(post -> postConverter.ConvertUpdateDtoToPost(postUpdateDto))
                .orElseThrow(() -> new RuntimeException("Not Found Post ID : " + postUpdateDto.getId())).getId();
    }

    @Transactional(readOnly = true)
    public PostSaveDto findOne(Long id) throws RuntimeException {
        return postRepository.findById(id)
                .map(postConverter::convertToPostDto)
                .orElseThrow(() -> new RuntimeException("Not Found Post ID : " + id));
    }

    @Transactional(readOnly = true)
    public Page<PostSaveDto> findAll(Pageable pageable) {
        PageRequest.of(10,10);
        return postRepository.findAll(pageable).map(postConverter::convertToPostDto);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post deletePost =  postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Can't find Post Id : {}"));
        postRepository.delete(deletePost);
    }
}
