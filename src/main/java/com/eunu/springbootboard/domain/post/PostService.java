package com.eunu.springbootboard.domain.post;

import com.eunu.springbootboard.entity.Post;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostConverter postConverter;

    @Transactional
    public Long save(PostDto dto) {
        Post post = postConverter.convertPost(dto);
        Post entity = postRepository.save(post);
        return entity.getId();
    }

    @Transactional
    public PostDto findOne(Long postId) throws NotFoundException {
        PostDto postDto = postRepository.findById(postId)
            .map(postConverter::convertPostDto)
            .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));

        return postDto;
    }

    @Transactional
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
            .map(postConverter::convertPostDto);
    }

    @Transactional
    public PostDto update(Long postId,PostDto dto) throws NotFoundException {
        if(!postId.equals(dto.getId())) {new NotFoundException("게시물 아이디가 일치하지 않습니다.");}

        Post entity = postRepository.findById(postId)
            .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        Post post = postConverter.convertPost(dto);

        entity.setTitle(post.getTitle());
        entity.setContent(post.getContent());

        return postConverter.convertPostDto(entity);
    }
}
