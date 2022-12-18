package kdt.springbootboardjpa.service;

import kdt.springbootboardjpa.respository.PostRepository;
import kdt.springbootboardjpa.respository.UserRepository;
import kdt.springbootboardjpa.respository.entity.Post;
import kdt.springbootboardjpa.respository.entity.User;
import kdt.springbootboardjpa.service.dto.PostDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find Post"));
    }

    public Post createPost(PostDto postDto) {
        User user = userRepository.findById(postDto.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("Can't find User"));
        Post newPost = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .user(user)
                .build();
        return postRepository.save(newPost);
    }

    public Post updatePost(Long postId, PostDto postDto) {
        Post savedPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Can't find Post"));
        Post newPost = Post.builder()
                .id(postId)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .user(savedPost.getUser())
                .build();
        return postRepository.save(newPost);
    }
}
