package com.study.board.domain.post.service;

import com.study.board.domain.exception.PostEditAccessDeniedException;
import com.study.board.domain.exception.PostNotFoundException;
import com.study.board.domain.exception.UserNotFoundException;
import com.study.board.domain.post.domain.Post;
import com.study.board.domain.post.domain.PostRepository;
import com.study.board.domain.user.domain.User;
import com.study.board.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Post findById(Long postId){
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    public Post write(String title, String content, Long writerId){
        User user = userRepository.findById(writerId).orElseThrow(() -> new UserNotFoundException(writerId));

        return postRepository.save(Post.create(title, content, user));
    }

    public Post edit(Long postId, String title, String content, Long editorId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        if(post.hasOfWriterId(editorId)){
            return post.update(title, content);
        }else{
            throw new PostEditAccessDeniedException(postId);
        }
    }
}
