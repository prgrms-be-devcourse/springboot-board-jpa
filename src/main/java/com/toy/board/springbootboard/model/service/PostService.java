package com.toy.board.springbootboard.model.service;

import com.toy.board.springbootboard.model.converter.PostConverter;
import com.toy.board.springbootboard.model.domain.Post;
import com.toy.board.springbootboard.model.domain.User;
import com.toy.board.springbootboard.model.dto.PostDto;
import com.toy.board.springbootboard.model.repository.PostRepository;
import com.toy.board.springbootboard.model.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, PostConverter postConverter, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
        this.userRepository = userRepository;
    }

    @Transactional
    public long save(PostDto postDto) throws NotFoundException {
        User user = userRepository.findById(postDto.getUserId())
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));
        Post post = postConverter.convertPost(postDto, user); // dto->entity
        Post entity = postRepository.save(post); // persist
        return entity.getId();
    }

    @Transactional(readOnly = true) // 값 변경 방지
    public PostDto findById(long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(post -> postConverter.convertPostDto(post))
                .orElseThrow(() -> new NotFoundException("조회오류: 포스트를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public Page<PostDto> findPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }

    @Transactional
    public long update(long id, PostDto postDto) {
        Post post = null;
        try {
            post = postRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("업데이트오류 : 포스트를 찾을 수 없습니다."));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        return post.getId();
    }
}
