package com.prgrms.be.app.service;

import com.prgrms.be.app.domain.Post;
import com.prgrms.be.app.domain.User;
import com.prgrms.be.app.domain.dto.PostDTO;
import com.prgrms.be.app.repository.PostRepository;
import com.prgrms.be.app.repository.UserRepository;
import com.prgrms.be.app.util.PostConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostConverter postConverter;

    @Transactional(readOnly = true)
    public PostDTO.PostsWithPaginationResponse findAll(Pageable pageable) {
        return postConverter.convertToPaginationResponse(postRepository.findAll(pageable));
//        return postRepository.findAll(pageable).map(postConverter::convertToPostsResponse);

    }

    @Transactional(readOnly = true)
    public PostDTO.PostDetailResponse findById(Long id) {
        return postRepository.findById(id)
                .map(postConverter::convertToPostDetailResponse)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
    }

    @Transactional
    public Long createPost(PostDTO.CreateRequest postCreateDto) {
        User user = userRepository.findById(postCreateDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        Post post = postConverter.covertToPost(postCreateDto, user);

        return postRepository.save(post).getId();
    }

    @Transactional
    public Long updatePost(PostDTO.UpdateRequest postUpdateDto, Long postId) {
        Post post = postRepository.findById(postId) // 이렇게 하는게 나을까 findById 반환 데이터 타입을 Post로 바꾸고 해당 메서드를 이용하는게 나을까?
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
        post.changePost(postUpdateDto.title(), postUpdateDto.content());
        return post.getId();
    }
}
