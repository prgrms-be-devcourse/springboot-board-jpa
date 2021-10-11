package yjh.jpa.springnoticeboard.domain.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjh.jpa.springnoticeboard.domain.converter.PostMapper;
import yjh.jpa.springnoticeboard.domain.converter.UserMapper;
import yjh.jpa.springnoticeboard.domain.dto.PostDto;
import yjh.jpa.springnoticeboard.domain.dto.UserDto;
import yjh.jpa.springnoticeboard.domain.entity.Post;
import yjh.jpa.springnoticeboard.domain.entity.User;
import yjh.jpa.springnoticeboard.domain.repository.PostRepository;
import yjh.jpa.springnoticeboard.domain.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public Long createPost(PostDto postDto) {
        Post post = PostMapper.INSTANCE.postDtoToEntity(postDto);
        post.setUser(postDto.getUser());
        post.setCreatedBy(postDto.getUser().getName());
        post.setCratedAt(LocalDateTime.now());
        postRepository.save(post);
        return post.getId();
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
        UserDto userDto = userRepository.findById(userId)
                        .map(user -> UserMapper.INSTANCE.userEntityToDto(user))
                        .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));
        Post post = postRepository.findById(postId).get();
        userDto.getPosts().remove(post);
        postRepository.deleteById(postId);
    }

    @Transactional(readOnly = true)
    @Override
    public void deleteAll() {
        postRepository.deleteAll();
    }
}
