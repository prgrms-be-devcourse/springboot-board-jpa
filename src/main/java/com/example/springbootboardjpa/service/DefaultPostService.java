package com.example.springbootboardjpa.service;

import com.example.springbootboardjpa.converter.PostConverter;
import com.example.springbootboardjpa.dto.PostDTO;
import com.example.springbootboardjpa.exception.NotFoundException;
import com.example.springbootboardjpa.model.Post;
import com.example.springbootboardjpa.repoistory.PostJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DefaultPostService implements PostService {

    private final PostJpaRepository postRepository;

    private final PostConverter postConverter;

    public DefaultPostService(PostJpaRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Override
    public PostDTO.Response findById(long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(postConverter::convertResponseOnlyPostDto)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public List<PostDTO> findAll() {
        return null;
    }

    @Override
    public long save(PostDTO.Save postDTO) {
        Post post = postConverter.convertNewPost(postDTO);
        log.info(post.toString());
        Post entity = postRepository.save(post);
        return entity.getId();
    }

    @Override
    public void update(long id, String title, String contents) throws NotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        post.changeTitle(title);
        post.changeContent(contents);
    }
}
