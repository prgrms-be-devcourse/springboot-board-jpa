package org.programmers.project_board.service;

import lombok.RequiredArgsConstructor;
import org.programmers.project_board.converter.PostConverter;
import org.programmers.project_board.converter.UserConverter;
import org.programmers.project_board.dto.PostDto;
import org.programmers.project_board.domain.Post;
import org.programmers.project_board.domain.User;
import org.programmers.project_board.exception.NotFoundException;
import org.programmers.project_board.repository.PostRepository;
import org.programmers.project_board.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostConverter postConverter;
    private final UserConverter userConverter;

    @Transactional
    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream().map(postConverter::convertPostDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostDto getPost(Long id) {
        return postRepository.findById(id)
                .map(postConverter::convertPostDto)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @Transactional
    public Long savePost(PostDto postDto) {
        User user = userConverter.convertUser(postDto.getUserDto());
        Post post = postConverter.convertPost(postDto);

        userRepository.save(user);
        Post entity = postRepository.save(post);

        return entity.getId();
    }

    @Transactional
    public Long updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

        post.update(postDto.getTitle(), postDto.getContent());
        Post entity = postRepository.save(post);

        return entity.getId();
    }

}
