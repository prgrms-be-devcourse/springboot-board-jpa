package com.toy.board.springbootboard.model.service;

import com.toy.board.springbootboard.model.converter.PostConverter;
import com.toy.board.springbootboard.model.domain.Post;
import com.toy.board.springbootboard.model.dto.PostDto;
import com.toy.board.springbootboard.model.repository.PostRepository;
import javassist.NotFoundException;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;

    @Autowired
    public PostService(PostRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    public long save (PostDto postDto){
        Post post = postConverter.convertPost(postDto); // dto->entity
        Post entity = postRepository.save(post); // persist
        return entity.getId();
    }

    public PostDto findById(long id) throws NotFoundException{
        return postRepository.findById(id)
                .map(post -> postConverter.convertPostDto(post))
                .orElseThrow(() -> new NotFoundException("조회오류: 포스트를 찾을 수 없습니다."));
    }

    public Page<PostDto> findPosts(Pageable pageable){
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }

    public long update(long id, PostDto postDto) throws NotFoundException{
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("업데이트오류 : 포스트를 찾을 수 없습니다."));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        return post.getId();
    }
}
