package com.kdt.post.service;

import com.kdt.post.converter.PostConverter;
import com.kdt.post.dto.PostControlRequestDto;
import com.kdt.post.dto.PostDto;
import com.kdt.post.model.Post;
import com.kdt.post.repository.PostRepository;
import com.kdt.user.model.User;
import com.kdt.user.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostConverter postConverter;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private static final String NOT_FOUNDED_MESSAGE = "this post is not valid";

    public Page<PostDto> findAll(Pageable pageable){
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }

    public PostDto find(Long postId) throws NotFoundException {
        return postRepository.findById(postId)
                .map(postConverter::convertPostDto)
                .orElseThrow(() -> new NotFoundException(NOT_FOUNDED_MESSAGE));
    }

    public Long save(PostControlRequestDto requestDto) throws NotFoundException {
        User user = getUser(requestDto.getUserId());

        Post post = postConverter.convertPost(requestDto);
        post.setUser(user);

        Post save = postRepository.save(post);
        return save.getId();
    }

    public Long update(Long postId, PostControlRequestDto requestDto) throws NotFoundException {
        if(postId == null || !postId.equals(requestDto.getPostId())){
            throw new NotFoundException(NOT_FOUNDED_MESSAGE);
        }

        User user = getUser(requestDto.getUserId());
        PostDto postDto = requestDto.getPostDto();
        Post post = user.getPosts().stream().filter(p -> p.getId().equals(postId))
                            .findAny()
                            .orElseThrow(() -> new NotFoundException(NOT_FOUNDED_MESSAGE));

        post.update(postDto.getTitle(), postDto.getContent());

        return postId;
    }

    public void delete(Long postId, PostControlRequestDto requestDto) throws NotFoundException {
        if(postId == null || !postId.equals(requestDto.getPostId())){
            throw new NotFoundException(NOT_FOUNDED_MESSAGE);
        }

        User user = getUser(requestDto.getUserId());
        boolean isDeleted = user.getPosts().removeIf(p -> p.getId().equals(postId));

        if(!isDeleted){
            throw new NotFoundException(NOT_FOUNDED_MESSAGE);
        }
    }

    private User getUser(Long userId) throws NotFoundException {
        if(userId == null){
            throw new NotFoundException("please sign in");
        }

        return userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException(NOT_FOUNDED_MESSAGE));
    }
}
