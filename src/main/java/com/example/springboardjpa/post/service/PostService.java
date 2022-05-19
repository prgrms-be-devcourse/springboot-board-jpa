package com.example.springboardjpa.post.service;

import com.example.springboardjpa.post.converter.PostConverter;
import com.example.springboardjpa.post.domain.Post;
import com.example.springboardjpa.post.dto.PostDto;
import com.example.springboardjpa.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import javassist.*;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;

    public PostService(PostRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public long save(PostDto dto){
        Post post = postConverter.convertPost(dto);
        Post entity = postRepository.save(post);
        return entity.getId();
    }

    @Transactional
    public PostDto findOne(long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(postConverter::convertPostDto)
                .orElseThrow(() -> new NotFoundException("주문을 찾을 수 없습니다."));
    }

    @Transactional
    public Page<PostDto> findAll(Pageable pageable){
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }

    @Transactional
    public void update(PostDto postDto, long id) throws NotFoundException {
        Optional<Post> post = postRepository.findById(id);

        if(post.isEmpty()){
            throw new NotFoundException("게시글이 존재하지 않습니다.");
        }
        post.get().changeTitle(postDto.getTitle());
        post.get().changeContent(postDto.getContent());
    }
}
