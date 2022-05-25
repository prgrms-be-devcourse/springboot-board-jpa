package com.programmers.board.service;

import com.programmers.board.dto.Convertor;
import com.programmers.board.dto.PostRequestDto;
import com.programmers.board.dto.PostResponseDto;
import com.programmers.board.entity.Post;
import com.programmers.board.entity.User;
import com.programmers.board.exception.NotFoundException;
import com.programmers.board.repository.PostRepository;
import com.programmers.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(PostRequestDto postRequestDto){
        Post post = Convertor.postRequestConvertor(postRequestDto);
        User user = userRepository.findById(postRequestDto.getUserId())
                .orElseThrow(() -> new NotFoundException("해당 유저가 존재하지 않습니다."));
        post.setUser(user);
        Post entity = postRepository.save(post);
        return entity.getId();
    }

    @Transactional
    public Page<PostResponseDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(Convertor::postResponseConvertor);
    }

    @Transactional
    public PostResponseDto find(Long id) throws NotFoundException{
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("데이터가 존재하지 않습니다"));
        return Convertor.postResponseConvertor(post);
    }

}
