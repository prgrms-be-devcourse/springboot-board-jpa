package com.kdt.post.service;

import com.kdt.post.converter.PostConverter;
import com.kdt.post.dto.PostDto;
import com.kdt.post.model.Post;
import com.kdt.post.repository.PostRepository;
import com.kdt.user.dto.UserDto;
import com.kdt.user.model.User;
import com.kdt.user.repository.UserRepository;
import com.kdt.user.service.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostConverter postConverter;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Page<PostDto> findAll(Pageable pageable){
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }

    public PostDto find(Long postId) throws NotFoundException {
        return postRepository.findById(postId)
                .map(postConverter::convertPostDto)
                .orElseThrow(() -> new NotFoundException("this post is not valid"));
    }

    public Long save(Long userId, PostDto postDto) throws NotFoundException {
        User user = getUser(userId);

        Post post = postConverter.convertPost(user.getName(), postDto);
        post.setUser(user);

        Post save = postRepository.save(post);
        return save.getId();
    }

    public Long update(Long userId, PostDto postDto) throws NotFoundException {
        User user = getUser(userId);
        int postIdx = findIndexOfPost(user.getPosts(), postDto.getId());
        Post post = user.getPosts().get(postIdx);

        post.update(postDto.getTitle(), postDto.getConent());

        return post.getId();
    }

    public void delete(Long userId, Long postId) throws NotFoundException {
        User user = getUser(userId);
        int postIdx = findIndexOfPost(user.getPosts(), postId);

        user.getPosts().remove(postIdx);
    }

    private User getUser(Long userId) throws NotFoundException {
        if(userId == null){
            throw new NotFoundException("please sign in");
        }

        return userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("this account is not valid"));
    }

    private int findIndexOfPost(List<Post> posts, Long postId) throws NotFoundException {
        for(int idx = 0; idx < posts.size(); idx++){
            if(posts.get(idx).getId().equals(postId)){
                return idx;
            }
        }

        throw new NotFoundException("this post is not valid");
    }
}
