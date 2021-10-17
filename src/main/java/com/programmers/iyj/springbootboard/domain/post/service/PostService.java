package com.programmers.iyj.springbootboard.domain.post.service;

import com.programmers.iyj.springbootboard.domain.post.domain.Post;
import com.programmers.iyj.springbootboard.domain.post.dto.PostDto;
import com.programmers.iyj.springbootboard.domain.post.mapper.PostMapper;
import com.programmers.iyj.springbootboard.domain.post.repository.PostRepository;
import com.programmers.iyj.springbootboard.domain.user.domain.User;
import com.programmers.iyj.springbootboard.domain.user.dto.UserDto;
import com.programmers.iyj.springbootboard.domain.user.mapper.UserMapper;
import com.programmers.iyj.springbootboard.domain.user.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostMapper mapper = Mappers.getMapper(PostMapper.class);

    private final PostRepository postRepository;

    public PostDto findOneById(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow(() -> new NotFoundException("No posts were found."));
    }

    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(mapper::entityToDto);
    }

    public Post save(PostDto postDto) {
        Post entity = mapper.dtoToEntity(postDto);
        return postRepository.save(entity);
    }
}
