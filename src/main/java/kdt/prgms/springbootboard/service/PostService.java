package kdt.prgms.springbootboard.service;

import java.text.MessageFormat;
import kdt.prgms.springbootboard.converter.PostConverter;
import kdt.prgms.springbootboard.dto.PostDetailResponseDto;
import kdt.prgms.springbootboard.dto.PostSaveRequestDto;
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
        PostConverter postConverter
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public PostDetailResponseDto save(PostSaveRequestDto postSaveRequestDto) {
        var foundUser = userRepository.findByName(postSaveRequestDto.getSimpleUserDto().getName())
            .orElseThrow(() -> new UserNotFoundException(postSaveRequestDto.getSimpleUserDto().getName()));
        var newPost = postConverter.convertPost(postSaveRequestDto, foundUser);
        return postConverter.convertPostDetailDto(postRepository.save(newPost));
    }

    @Transactional
    public PostDetailResponseDto update(Long postId, PostSaveRequestDto postSaveRequestDto) {
        var foundPost = postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException(
                MessageFormat.format("{0}({1})", postSaveRequestDto.getTitle(), postSaveRequestDto.getId())));
        foundPost.changePost(postSaveRequestDto.getTitle(), postSaveRequestDto.getContent());
        return postConverter.convertPostDetailDto(foundPost);
    }

    public Page<PostSaveRequestDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(postConverter::convertPostDto);
    }

    public PostDetailResponseDto findOne(Long postId) {
        return postRepository.findById(postId)
            .map(postConverter::convertPostDetailDto)
            .orElseThrow(() -> new PostNotFoundException(
                MessageFormat.format("post({0})", postId)));
    }
}
