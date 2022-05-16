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

import java.util.Optional;

import static com.kdt.boardMission.domain.Post.createPost;
import static com.kdt.boardMission.dto.PostDto.convertPostDto;
import static com.kdt.boardMission.dto.UserDto.convertUser;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public long savePost(PostDto postDto, UserDto userDto) throws NotFoundException {
        Optional<User> userById = userRepository.findById(userDto.getId());
        if (userById.isEmpty()) {
            throw new NotFoundException("해당 아이디를 가진 유저가 없습니다.");
        }
        Post post = createPost(userById.get(), postDto.getTitle(), postDto.getContent());
        Post save = postRepository.save(post);
        return save.getId();
    }

    public void updateTitle(PostDto postDto) throws NotFoundException {
        Optional<Post> postById = postRepository.findById(postDto.getId());
        if (postById.isEmpty()) {
            throw new NotFoundException("존재하지 않는 게시물입니다.");
        }
        postById.get().updateTitle(postDto.getTitle());
    }

    public void updateContent(PostDto postDto) throws NotFoundException {
        Optional<Post> postById = postRepository.findById(postDto.getId());
        if (postById.isEmpty()) {
            throw new NotFoundException("존재하지 않는 게시물입니다.");
        }
        postById.get().updateContent(postDto.getContent());
    }

    public void deletePost(PostDto postDto) throws NotFoundException {
        Optional<Post> postById = postRepository.findById(postDto.getId());
        if (postById.isEmpty()) {
            throw new NotFoundException("존재하지 않는 게시물입니다.");
        }
        postById.get().deletePost();
        postRepository.deleteById(postDto.getId());
    }

    public Page<PostDto> findByTitle(String title, Pageable pageable) {
        return postRepository.findByTitle(title, pageable).map(PostDto::convertPostDto);
    }

    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostDto::convertPostDto);
    }
}
