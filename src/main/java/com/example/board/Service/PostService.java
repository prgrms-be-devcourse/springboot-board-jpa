package com.example.board.Service;

import com.example.board.Dto.PostDto;
import com.example.board.Repository.PostRepository;
import com.example.board.converter.Converter;
import com.example.board.domain.Post;
import com.example.board.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    Converter converter;

    @Transactional
    public String save(PostDto post){
        var resultPost = converter.convertpost(post);
        postRepository.save(resultPost);
        return resultPost.getTitle();
    }

    @Transactional
    public void update(Long id){


    }
    @Transactional
    public void findOne(Long id){
        postRepository.findById(id);

    }
    @Transactional
    public void findAll(){
        postRepository.findAll();

    }

}
