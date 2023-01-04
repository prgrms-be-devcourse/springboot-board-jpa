package com.example.springbootboardjpa.service;

import com.example.springbootboardjpa.converter.PostConverter;
import com.example.springbootboardjpa.dto.PostDTO;
import com.example.springbootboardjpa.exception.NotFoundException;
import com.example.springbootboardjpa.model.Post;
import com.example.springbootboardjpa.model.User;
import com.example.springbootboardjpa.repoistory.PostJpaRepository;
import com.example.springbootboardjpa.repoistory.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultPostService implements PostService {

    private final PostJpaRepository postRepository;

    private final UserJpaRepository userJpaRepository;

    private final PostConverter postConverter;

    @Override
    public PostDTO.Response findById(long id) {
        return postRepository.findById(id)
                .map(postConverter::convertResponseOnlyPostDto)
                .orElseThrow(() -> new NotFoundException("해당 Id의 게시물을 찾을 수 없습니다."));
    }

    @Override
    public Long save(PostDTO.Save postDTO) {
        Optional<User> findUser = userJpaRepository.findById(postDTO.getUserId());
        Post post = findUser.map(user -> postConverter.convertNewPost(postDTO,user))
                .orElseThrow(() -> new NotFoundException("해당 Id의 사용자를 찾을 수 없습니다."));
        postRepository.save(post);
        return post.getId();
    }

    @Override
    public void update(long id, String title, String contents) {
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        post.changePost(title ,contents);
    }

    @Override
    public Page<PostDTO.Response> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertResponseOnlyPostDto);
    }

}
