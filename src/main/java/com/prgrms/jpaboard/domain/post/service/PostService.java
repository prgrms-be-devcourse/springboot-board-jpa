package com.prgrms.jpaboard.domain.post.service;

import com.prgrms.jpaboard.domain.post.domain.Post;
import com.prgrms.jpaboard.domain.post.domain.PostRepository;
import com.prgrms.jpaboard.domain.post.dto.*;
import com.prgrms.jpaboard.domain.post.exception.PostNotFoundException;
import com.prgrms.jpaboard.domain.user.domain.User;
import com.prgrms.jpaboard.domain.user.domain.UserRepository;
import com.prgrms.jpaboard.domain.user.exception.UserNotFoundException;
import com.prgrms.jpaboard.global.common.response.MetaDataDto;
import com.prgrms.jpaboard.global.common.response.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResultDto createPost(PostRequestDto postRequestDto) {
        User user = userRepository.findById(postRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException());

        Post post = postRequestDto.toEntity();
        post.setUser(user);

        Post savedPost = postRepository.save(post);

        return ResultDto.createResult(savedPost.getId());
    }

    @Transactional(readOnly = true)
    public PostListDto getPosts(int page, int perPage) {
        Page<Post> postPagingResult = postRepository.findAllWithUser(PageRequest.of(page, perPage, Sort.by(Sort.Direction.DESC, "createdAt")));

        MetaDataDto metadata = MetaDataDto.builder()
                .page(page)
                .totalPage(postPagingResult.getTotalPages())
                .perPage(perPage)
                .total(postPagingResult.getTotalElements())
                .build();

        List<PostDto> posts = postPagingResult.getContent()
                .stream().map(post -> PostDto.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .user(new UserInfoDto(post.getUser().getId(), post.getUser().getName()))
                            .createdAt(post.getCreatedAt())
                            .build())
                .collect(Collectors.toList());

        return new PostListDto(metadata, posts);
    }

    @Transactional(readOnly = true)
    public PostDetailDto getPost(Long id) {
        Post post = postRepository.findByIdWithUser(id).orElseThrow(() -> new PostNotFoundException());

        return PostDetailDto.postDetailDtoBuilder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .user(new UserInfoDto(post.getUser().getId(), post.getUser().getName()))
                .build();
    }
}
