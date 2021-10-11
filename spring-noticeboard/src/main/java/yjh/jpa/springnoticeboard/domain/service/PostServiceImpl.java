package yjh.jpa.springnoticeboard.domain.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    @Override
    public Long createPost(PostDto postDto) throws NotFoundException {
        Post post = PostMapper.INSTANCE.postDtoToEntity(postDto);
        var u = userRepository.findById(postDto.getUser().getId()).orElseThrow(()->new NotFoundException("회워을 찾을 수 없습니다."));
        u.addPost(post);
        post.setCreatedBy(postDto.getUser().getName());
        post.setCratedAt(LocalDateTime.now());
        return postRepository.save(post).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public Long updatePost(Long postId, PostDto postDto) throws NotFoundException {
        PostDto update = postRepository.findById(postId)
                .map(post -> PostMapper.INSTANCE.postToDto(post))
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        update.setContent(postDto.getContent());
        update.setTitle(postDto.getTitle());
        return update.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Object> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(post -> PostMapper.INSTANCE.postToDto(post));
    }

    @Transactional(readOnly = true)
    @Override
    public PostDto findPost(Long postId) throws NotFoundException {
        return postRepository.findById(postId)
                .map(post -> PostMapper.INSTANCE.postToDto(post))
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    @Override
    public void deletePost(Long postId, Long userId) throws NotFoundException {
        var user = userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));
        Post post = postRepository.findById(postId).get();
        user.getPosts().remove(post);
        postRepository.deleteById(postId);
    }

    @Transactional(readOnly = true)
    @Override
    public void deleteAll() {
        postRepository.deleteAll();
    }
}
