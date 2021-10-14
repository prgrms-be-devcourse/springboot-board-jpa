package kdt.prgrms.devrun.post.service;

import kdt.prgrms.devrun.common.exception.PostNotFoundException;
import kdt.prgrms.devrun.common.exception.UserNotFoundException;
import kdt.prgrms.devrun.domain.Post;
import kdt.prgrms.devrun.domain.User;
import kdt.prgrms.devrun.post.dto.AddPostRequestDto;
import kdt.prgrms.devrun.post.dto.DetailPostDto;
import kdt.prgrms.devrun.post.dto.EditPostRequestDto;
import kdt.prgrms.devrun.post.dto.SimplePostDto;
import kdt.prgrms.devrun.post.repository.PostRepository;
import kdt.prgrms.devrun.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SimplePostService implements PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public Page<SimplePostDto> getPostPagingList(Pageable pageable) {
        return postRepository.findAll(pageable).map(post -> {
            return SimplePostDto.builder()
                .id(post.getId())
                .title(post.getContent())
                .createdAt(post.getCreatedAt())
                .createdBy(post.getUser().getLoginId())
                .build();
        });
    }

    @Override
    public DetailPostDto getPostById(Long postId) {

        Post foundPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException());

        return DetailPostDto.builder()
            .id(foundPost.getId())
            .title(foundPost.getTitle())
            .content(foundPost.getContent())
            .createdAt(foundPost.getCreatedAt())
            .createdBy(foundPost.getUser().getLoginId())
            .build();

    }

    @Override
    @Transactional
    public Long createPost(AddPostRequestDto addPostRequestDto) {
        final User foundUser = userRepository.findUsersByLoginId(addPostRequestDto.getCreatedBy()).orElseThrow(() -> new UserNotFoundException());
        final Post newPost = addPostRequestDto.convertToEntity(foundUser);

        final Post savedPost = postRepository.save(newPost);
        return savedPost.getId();
    }

    @Override
    @Transactional
    public Long updatePost(Long postId , EditPostRequestDto editPostRequestDto) {

        Post foundPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException());
        foundPost.updatePost(editPostRequestDto.getTitle(), editPostRequestDto.getContent());

        return postId;
    }

    @Override
    @Transactional
    public void deletePostById(Long postId) {
        Post foundPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException());
        postRepository.delete(foundPost);
    }

}
