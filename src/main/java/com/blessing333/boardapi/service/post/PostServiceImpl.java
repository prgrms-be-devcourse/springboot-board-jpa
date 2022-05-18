package com.blessing333.boardapi.service.post;

import com.blessing333.boardapi.controller.dto.PostCreateCommand;
import com.blessing333.boardapi.controller.dto.PostInformation;
import com.blessing333.boardapi.controller.dto.PostUpdateCommand;
import com.blessing333.boardapi.converter.PostConverter;
import com.blessing333.boardapi.entity.Post;
import com.blessing333.boardapi.entity.User;
import com.blessing333.boardapi.entity.exception.PostCreateFailException;
import com.blessing333.boardapi.entity.exception.PostUpdateFailException;
import com.blessing333.boardapi.repository.PostRepository;
import com.blessing333.boardapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Optional<Post> foundPost = postRepository.findPostWithMemberById(id);
        Post post = foundPost.orElseThrow(() -> new PostNotFoundException("post not exist"));
        return converter.fromPost(post);
    }

    @Override
    public Page<PostInformation> loadPostsWithPaging(Pageable pageable) {
        Page<Post> found = postRepository.findAll(pageable);
        return found.map(converter::fromPost);
    }

    @Override
    @Transactional
    public PostInformation registerPost(PostCreateCommand commands) {
        User writer = userRepository.findById(commands.getUserId()).orElseThrow(() -> new PostCreateFailException("invalid user"));
        Post post = Post.createNewPost(commands.getTitle(), commands.getContent(), writer);
        post = postRepository.save(post);
        return converter.fromPost(post);
    }

    @Override
    @Transactional
    public PostInformation updatePost(PostUpdateCommand command) {
        Optional<Post> found = postRepository.findPostWithMemberById(command.getId());
        Post post = found.orElseThrow(() -> new PostUpdateFailException("invalid post id"));
        post.changeTitle(command.getTitle());
        post.changeContent(command.getContent());
        return converter.fromPost(post);
    }
}
