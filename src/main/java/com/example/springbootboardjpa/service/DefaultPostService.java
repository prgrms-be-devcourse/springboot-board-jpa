package com.example.springbootboardjpa.service;

import com.example.springbootboardjpa.converter.PostConverter;
import com.example.springbootboardjpa.dto.PostDTO;
import com.example.springbootboardjpa.exception.NotFoundException;
import com.example.springbootboardjpa.model.Post;
import com.example.springbootboardjpa.repoistory.PostJpaRepository;
import com.example.springbootboardjpa.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultPostService implements PostService {

    private final PostJpaRepository postRepository;

    private final ValidationUtil validationUtil;

    private final PostConverter postConverter;

    @Override
    public PostDTO.Response findById(long id) {
        return postRepository.findById(id)
                .map(post -> {
                    validationUtil.validation(post); //
                    return postConverter.convertResponseOnlyPostDto(post);
                })
                .orElseThrow(() -> new NotFoundException("해당 Id의 게시물을 찾을 수 없습니다."));
    }

    @Override
    public long save(PostDTO.Save postDTO) {
        Post post = postConverter.convertNewPost(postDTO); // 1. find user 2. convert 3. validation 4. save 5. return
        validationUtil.validation(post);
        Post entity = postRepository.save(post);
        return entity.getId();
    }

    @Override
    public void update(long id, String title, String contents) {
        log.info("start method update");
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        post.changeTitle(title);
        post.changeContent(contents);
        validationUtil.validation(post);
        log.info("end method update");
    }

    @Override
    public Page<PostDTO.Response> findAll(Pageable pageable) { // need value Slice Pageable page
        return postRepository.findAll(pageable)
                .map(post -> {
                    validationUtil.validation(post); // duplicate email, duplicate name
                    return postConverter.convertResponseOnlyPostDto(post);
                });
    }

}
