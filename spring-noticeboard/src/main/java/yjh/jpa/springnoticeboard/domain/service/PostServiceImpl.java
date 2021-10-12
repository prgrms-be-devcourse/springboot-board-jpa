package yjh.jpa.springnoticeboard.domain.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjh.jpa.springnoticeboard.domain.converter.Converter;
import yjh.jpa.springnoticeboard.domain.converter.PostMapper;
import yjh.jpa.springnoticeboard.domain.converter.UserMapper;
import yjh.jpa.springnoticeboard.domain.converter.UserMapperImpl;
import yjh.jpa.springnoticeboard.domain.dto.PostDto;
import yjh.jpa.springnoticeboard.domain.dto.UserDto;
import yjh.jpa.springnoticeboard.domain.entity.Post;
import yjh.jpa.springnoticeboard.domain.entity.User;
import yjh.jpa.springnoticeboard.domain.repository.PostRepository;
import yjh.jpa.springnoticeboard.domain.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostServiceImpl implements PostService{

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Converter converter;

    @Transactional
    @Override
    public Long createPost(PostDto postDto) throws NotFoundException {
        log.info("postDto {} ", postDto.getId());
        log.info("postDto {} ", postDto.getUser());
        log.info("postDto {} ", postDto.getContent());
        log.info("postDto {} ", postDto.getTitle());
        var u = userRepository.findById(postDto.getUser().getId()).orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));
        log.info("User {} ", u);
        Post post = converter.postDtoToEntity(postDto);
        log.info("post {} ", post.getId());
        log.info("post {} ", post.getUser());
        log.info("post {} ", post.getContent());
        log.info("post {} ", post.getTitle());

        post.setCreatedBy(u.getName());
        post.setCratedAt(LocalDateTime.now());
        post.setUser(u);
        Post newPost = postRepository.save(post);
        return newPost.getId();
    }

    @Transactional
    @Override
    public Long updatePost(Long postId, PostDto postDto) throws NotFoundException {
        var user = userRepository.findById(postDto.getUser().getId()).orElseThrow(() -> new NotFoundException("회원을 찾을 수 없음"));
        Post update = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        update.updatePost(postDto.getTitle(),postDto.getContent(),user);
        return update.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Object> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(post -> converter.postToDto(post));
    }

    @Transactional(readOnly = true)
    @Override
    public PostDto findPost(Long postId) throws NotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        post.setUser(post.getUser());
        return  converter.postToDto(post);
    }

    @Transactional
    @Override
    public void deletePost(Long postId) throws NotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        post.getUser().getPosts().removeIf(e-> e.getId().equals(postId));
        postRepository.deleteById(postId);
    }

    @Transactional
    @Override
    public void deleteAll() {
        postRepository.deleteAll();
    }
}
