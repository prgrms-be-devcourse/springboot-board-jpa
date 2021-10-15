package com.programmers.board.service;

import com.programmers.board.converter.PostConverter;
import com.programmers.board.domain.Post;
import com.programmers.board.dto.PostDto;
import com.programmers.board.repository.PostRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

//    @Autowired // 필드 주입
//    private PostRepository postRepository;
//
//    @Autowired
//    private PostConverter postConverter;

    /*생성자 주입 방식을 쓴 이유: 필드를 @Autowired로 주입하는 것에 비해 순환 참조를 막을 수 있기 때문*/
    private final PostRepository postRepository;
    private final PostConverter postConverter;
    public PostService(PostRepository postRepository, PostConverter postConverter){
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public Long save(PostDto postDto){
        // 1. dto -> entity 변환 (준영속)
        Post post = postConverter.convertPost(postDto);
        // 2. postRepository.save(post) -> 영속화
        Post entity = postRepository.save(post);
        return entity.getId(); // 3. 결과 반환
    }

    @Transactional
    public PostDto findOneById(Long id) throws NotFoundException {
        // 1. 게시판 조회를 위한 아이디를 인자로 받기
        // 2. postRepository.findById(id) -> 조회 (영속화된 엔티티)
        return postRepository.findById(id)
                .map(postConverter::convertPostDto) // 3. entity -> dto
                .orElseThrow(() -> new NotFoundException("원하는 게시물을 찾을 수 없습니다"));
    }

    @Transactional
    public Page<PostDto> findAll(Pageable pageable){
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }
}
