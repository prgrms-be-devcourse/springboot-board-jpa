package com.kdt.boardMission.service;

import com.kdt.boardMission.domain.Post;
import com.kdt.boardMission.domain.User;
import com.kdt.boardMission.dto.PostDto;
import com.kdt.boardMission.dto.UserDto;
import com.kdt.boardMission.repository.PostRepository;
import com.kdt.boardMission.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kdt.boardMission.dto.PostDto.convertPostDto;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public long savePost(PostDto postDto, UserDto userDto) throws NotFoundException {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new NotFoundException("해당 아이디를 가진 유저가 없습니다."));
        Post post = new Post(user, postDto.getTitle(), postDto.getContent());
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    public void updatePost(PostDto postDto) throws NotFoundException {
        Post post = postRepository.findById(postDto.getId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다."));
        post.updateTitle(postDto.getTitle());
        post.updateContent(postDto.getContent());

    }

    public void deletePost(PostDto postDto) throws NotFoundException {
        Post post = postRepository.findById(postDto.getId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다."));
        post.deletePost();
        postRepository.deleteById(postDto.getId());
    }

    @Transactional(readOnly = true)
    public PostDto findById(long postId) throws NotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시물이 없습니다."));
        return convertPostDto(post);
    }

    @Transactional(readOnly = true)
    public Page<PostDto> findByTitle(String title, Pageable pageable) {
        return postRepository.findByTitle(title, pageable).map(PostDto::convertPostDto);
    }

    @Transactional(readOnly = true)
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostDto::convertPostDto);
    }
}
