package com.prgrms.boardjpa.post.service;

import com.prgrms.boardjpa.domain.Post;
import com.prgrms.boardjpa.domain.User;
import com.prgrms.boardjpa.exception.NotFoundException;
import com.prgrms.boardjpa.post.dao.PostRepository;
import com.prgrms.boardjpa.post.dto.PostReqDto;
import com.prgrms.boardjpa.post.dto.PostResDto;
import com.prgrms.boardjpa.post.dto.PostUpdateDto;
import com.prgrms.boardjpa.user.dao.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostResDto findOne(Long id) {
        return postRepository.findById(id)
                .map(Post::toResDto)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
    }

    public Page<PostResDto> findPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(Post::toResDto);
    }

    public PostResDto save(PostReqDto postReqDto) {
        Post post = postReqDto.toEntity();
        User author = userRepository.findById(postReqDto.getUserId()).orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
        post.setAuthor(author);
        postRepository.save(post);
        return post.toResDto();
    }

    public PostResDto update(Long postId, PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시글입니다."));
        post.setContent(postUpdateDto.getContent());
        post.setTitle(postUpdateDto.getTitle());
        return post.toResDto();
    }
}
