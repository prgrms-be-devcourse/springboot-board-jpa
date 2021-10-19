package kdt.prgms.springbootboard.service;

import java.text.MessageFormat;
import kdt.prgms.springbootboard.converter.PostConverter;
import kdt.prgms.springbootboard.dto.PostDetailDto;
import kdt.prgms.springbootboard.dto.PostDto;
import kdt.prgms.springbootboard.global.error.exception.PostNotFoundException;
import kdt.prgms.springbootboard.global.error.exception.UserNotFoundException;
import kdt.prgms.springbootboard.repository.PostRepository;
import kdt.prgms.springbootboard.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostConverter postConverter;

    public PostService(
        PostRepository postRepository,
        UserRepository userRepository,
        PostConverter postConverter) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public Long save(PostDto postDto) {
        var foundUser = userRepository.findByName(postDto.getUserDto().getName())
            .orElseThrow(() -> new UserNotFoundException(postDto.getUserDto().getName()));
        var newPost = postConverter.convertPost(postDto, foundUser);
        var savedPostEntity = postRepository.save(newPost);
        return postRepository.save(savedPostEntity).getId();
    }

    @Transactional
    public Long update(Long postId, PostDto postDto) {
        var foundPost = postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException(
                MessageFormat.format("{0}({1})", postDto.getTitle(), postDto.getId())));
        foundPost.changePost(postDto.getTitle(), postDto.getContent());
        return foundPost.getId();
    }

    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(postConverter::convertPostDto);
    }

    public PostDetailDto findOne(Long postId) {
        return postRepository.findById(postId)
            .map(postConverter::convertPostDetailDto)
            .orElseThrow(() -> new PostNotFoundException(
                MessageFormat.format("post({0})", postId)));
    }

}
