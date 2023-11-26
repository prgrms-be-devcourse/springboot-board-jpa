package com.prgrms.board.service;

import com.prgrms.board.domain.Post;
import com.prgrms.board.domain.User;
import com.prgrms.board.repository.PostRepository;
import com.prgrms.board.repository.UserRepository;
import com.prgrms.board.service.dto.PostDto;
import com.prgrms.board.service.dto.PostListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long create(Long userId, PostDto postDto) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = new Post(postDto.getTitle(), postDto.getContent(), user);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional
    public Long update(Long postId, String content) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.setContent(content);
        return postId;
    }

    @Transactional(readOnly = true)
    public PostListDto findAll(Pageable pageable){
        Page<Post> postList = postRepository.findAll(pageable);
        List<PostDto> postDtoList = postList.stream()
                .map(it -> new PostDto(it.getId(), it.getTitle(), it.getContent()))
                .collect(Collectors.toList());
        return new PostListDto(postList.getSize(), postDtoList);
    }

    @Transactional(readOnly = true)
    public PostDto findById(Long postId){
        Post findPost = postRepository.findById(postId)
                .orElseThrow();
        return new PostDto(findPost.getId(), findPost.getTitle(), findPost.getContent());
    }
}
