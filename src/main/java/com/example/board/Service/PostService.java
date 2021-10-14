package com.example.board.Service;

import com.example.board.Dto.PostDto;
import com.example.board.Repository.PostRepository;
import com.example.board.converter.Converter;
import com.example.board.domain.Post;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    Converter converter;

    @Transactional
    public Long save(PostDto post){
        var resultPost = converter.convertpost(post);
        Post save = postRepository.save(resultPost);
        return save.getPostId();
    }

    @Transactional
    public PostDto update(Long id, String title,String content) throws NotFoundException {
        Post post=postRepository.findById(id).orElseThrow(()->new NotFoundException("post를 찾을 수 없어 update에 실패했습니다"));
            post.setContent(content);
            post.setTitle(title);
        return converter.convertPostDto(post);

    }

    @Transactional(readOnly = true)
    public PostDto findOne(Long id) throws NotFoundException {

        return postRepository.findById(id)
                .map(converter::convertPostDto)
                .orElseThrow(()->new NotFoundException("post를 찾을 수 없습니다."));

    }

    @Transactional(readOnly = true)
    public Page<PostDto> findAll(Pageable pageable){
        return postRepository.findAll(pageable)
                        .map(converter::convertPostDto);

    }

}
