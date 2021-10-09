package org.programmers.project_board.service;

import lombok.RequiredArgsConstructor;
import org.programmers.project_board.converter.PostConverter;
import org.programmers.project_board.dto.PostDto;
import org.programmers.project_board.entity.Post;
import org.programmers.project_board.exception.NotFoundException;
import org.programmers.project_board.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    private final PostConverter postConverter;

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
        Post post = postConverter.convertPost(postDto);

        Post entity = postRepository.save(post);

        return entity.getId();
    }

}
