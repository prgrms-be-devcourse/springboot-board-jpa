package yjh.jpa.springnoticeboard.domain.service;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yjh.jpa.springnoticeboard.domain.dto.PostDto;
import yjh.jpa.springnoticeboard.domain.dto.UserDto;

public interface PostService {

    Long createPost(PostDto postDto) throws NotFoundException;
    Page<Object> findAll(Pageable pageable);
    PostDto findPost(Long postId) throws NotFoundException;
    Long updatePost(Long postId, PostDto postDto) throws NotFoundException;
    void deletePost(Long postId) throws NotFoundException;
    void deleteAll();

}
