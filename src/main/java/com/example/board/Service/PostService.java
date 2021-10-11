package com.example.board.Service;

import com.example.board.Dto.PostRequestDto;
import com.example.board.Repository.PostRepository;
import com.example.board.converter.Converter;
import com.example.board.domain.Post;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    Converter converter;

    @Transactional
    public Post save(PostRequestDto post){
        var resultPost = converter.convertpost(post);
        return postRepository.save(resultPost);
    }

    @Transactional
    public Post update(Long id, PostRequestDto postRequestDto) throws NotFoundException {
        Optional<Post> updatePost =postRepository.findById(id);


        if(!updatePost.isPresent()){
            throw new NotFoundException("post를 찾을 수 없어 update에 실패했습니다");
        }


        updatePost.get().setContent(postRequestDto.getContent());
        updatePost.get().setTitle(postRequestDto.getTitle());
//        System.out.println(updatePost.get().getUser().getName());
        return updatePost.get();

    }

    @Transactional
    public PostRequestDto findOne(Long id) throws NotFoundException {

        return postRepository.findById(id)
                .map(converter::convertPostDto)
                .orElseThrow(()->new NotFoundException("post를 찾을 수 없습니다."));

    }

    @Transactional
    public Page<PostRequestDto> findAll(Pageable pageable){
        return postRepository.findAll(pageable)
                        .map(converter::convertPostDto);

    }

}
