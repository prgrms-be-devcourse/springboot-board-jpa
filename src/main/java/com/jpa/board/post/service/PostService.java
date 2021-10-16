package com.jpa.board.post.service;

import com.jpa.board.post.Post;
import com.jpa.board.post.PostConverter;
import com.jpa.board.post.dto.PostCreateDto;
import com.jpa.board.post.dto.PostUpdateDto;
import com.jpa.board.post.repository.PostRepository;
import com.jpa.board.post.dto.PostReadDto;
import com.jpa.board.user.User;
import com.jpa.board.user.UserRepository;
import javassist.NotFoundException;
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

    private final PostConverter postConverter;

    @Transactional
    public Page<PostReadDto> findAll(Pageable pageable){
        return postRepository.findAll(pageable).map(postConverter::postToPostReadDto);
    }

    @Transactional
    public PostReadDto findById(Long id) throws NotFoundException {
        return postRepository.findById(id)
                .map(postConverter::postToPostReadDto)
                .orElseThrow(() -> new NotFoundException("해당 아이디에 대한 게시글을 찾을 수 없습니다."));
    }

    @Transactional
    public String save(PostCreateDto postCreateDto) throws NotFoundException{
        //아직 로그인 기능이 없어서 기존에 등록되어있던 userId로 user 를 찾아서 해당 user 의 글로 등록했습니다.
        User user = userRepository.findById(postCreateDto.getUserId()).orElseThrow(() -> new NotFoundException("등록된 사용자 없음"));

        Post post = postRepository.save(postConverter.postCreateDtoToPost(postCreateDto, user));
        return post.getUser().getName()+" 님이 작성한" + post.getTitle() + "등록";
    }

    @Transactional
    public String update(PostUpdateDto postUpdateDto, Long id) throws NotFoundException{
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("게시글 번호 없음"));
        post.setContent(postUpdateDto.getContent());
        post.setTitle(postUpdateDto.getTitle());
        postRepository.save(post);
        return post.getId() +" 번째 글 수정 됨";
    }
}
