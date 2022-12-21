package com.spring.board.springboard.post.service;

import com.spring.board.springboard.post.domain.Post;
import com.spring.board.springboard.post.domain.dto.RequestPostDTO;
import com.spring.board.springboard.post.domain.dto.ResponsePostDTO;
import com.spring.board.springboard.post.domain.dto.UpdatePostDTO;
import com.spring.board.springboard.post.exception.NoPostException;
import com.spring.board.springboard.post.repository.PostRepository;
import com.spring.board.springboard.user.domain.Member;
import com.spring.board.springboard.user.domain.MemberResponseDTO;
import com.spring.board.springboard.user.service.MemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final MemberService memberService;

    public PostService(PostRepository postRepository, MemberService memberService) {
        this.postRepository = postRepository;
        this.memberService = memberService;
    }

    @Transactional(readOnly = true)
    public List<ResponsePostDTO> getAll(Pageable pageable) {
        List<Post> postList = postRepository.findAll(pageable).getContent();
        return postList.stream()
                .map(post -> new ResponsePostDTO(
                        post, new MemberResponseDTO(post.getMember())))
                .toList();
    }

    @Transactional(readOnly = true)
    public ResponsePostDTO getOne(Integer postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> {
                    throw new NoPostException(
                            MessageFormat.format("해당 id ({0})의 게시물이 없습니다.", postId));
                });

        return new ResponsePostDTO(
                findPost, new MemberResponseDTO(findPost.getMember())
        );
    }

    @Transactional
    public ResponsePostDTO createPost(RequestPostDTO requestPostDTO) {
        Member findMember = memberService.find(requestPostDTO.getMemberId());

        Post post = requestPostDTO.toEntity(requestPostDTO, findMember);
        postRepository.save(post);

        return new ResponsePostDTO(post, new MemberResponseDTO(findMember));
    }

    @Transactional
    public ResponsePostDTO update(Integer postId, UpdatePostDTO updatePostDTO) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> {
                    throw new NoPostException(
                            MessageFormat.format("해당 id ({0})의 게시물이 없습니다.", postId)
                    );
                });

        findPost.changeTitle(
                updatePostDTO.getTitle()
        );

        findPost.changeContent(
                updatePostDTO.getContent()
        );

        return new ResponsePostDTO(findPost, new MemberResponseDTO(findPost.getMember()));
    }
}
