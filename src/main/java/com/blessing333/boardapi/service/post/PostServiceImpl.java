package com.blessing333.boardapi.service.post;

import com.blessing333.boardapi.controller.dto.PostCreateCommands;
import com.blessing333.boardapi.controller.dto.PostInformation;
import com.blessing333.boardapi.converter.PostConverter;
import com.blessing333.boardapi.entity.Post;
import com.blessing333.boardapi.entity.User;
import com.blessing333.boardapi.entity.exception.PostCreateFailException;
import com.blessing333.boardapi.repository.PostRepository;
import com.blessing333.boardapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostConverter converter;

    @Override
    public PostInformation loadPostById(Long id) {
        Optional<Post> foundPost = postRepository.findPostByIdWithMember(id);
        Post post = foundPost.orElseThrow(() -> new PostNotFoundException("post not exist"));
        return converter.fromPost(post);
    }

    @Override
    @Transactional
    public PostInformation registerPost(PostCreateCommands commands) {
        User writer = userRepository.findById(commands.getUserId()).orElseThrow(() -> new PostCreateFailException("invalid user"));
        Post post = Post.createNewPost(commands.getTitle(), commands.getContent(), writer);
        post = postRepository.save(post);
        return converter.fromPost(post);
    }
}
