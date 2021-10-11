package com.programmers.springbootboard.post.application;

import com.programmers.springbootboard.exception.ErrorMessage;
import com.programmers.springbootboard.exception.error.NotFoundException;
import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.infrastructure.MemberRepository;
import com.programmers.springbootboard.post.converter.PostConverter;
import com.programmers.springbootboard.post.domain.Post;
import com.programmers.springbootboard.post.domain.vo.Content;
import com.programmers.springbootboard.post.domain.vo.Title;
import com.programmers.springbootboard.post.dto.PostDetailResponse;
import com.programmers.springbootboard.post.dto.PostInsertRequest;
import com.programmers.springbootboard.post.dto.PostUpdateRequest;
import com.programmers.springbootboard.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final MemberRepository memberRepository; // 파사드 레이어를 만들어서 써보자!! or memberservice를 사용하는 것은?
    private final PostConverter postConverter;
    private final PostRepository postRepository;

    // 컨벤션으로 확고하게 가져가자!!

    @Transactional
    public void insert(Email email, PostInsertRequest request) {
        memberRepository.findByEmail(email)
                .map(member -> {
                    Post post = postConverter.toPost(request, member);

// 사용자 편의 메서드로 묶어주기!! 엔티티에서 동작하게!!
                    member.getPosts().addPost(post);
                    post.addByInformation(member.getId());
// 사용자 편의 메서드로 묶어주기!! 엔티티에서 동작하게!!

                    return post;
                })
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });
    }

    @Transactional
    public void update(Email email, Long id, PostUpdateRequest request) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });
        postRepository.findById(id)
                .map(post -> {
                    post.update(new Title(request.getTitle()), new Content(request.getContent()));
                    post.lastModifiedId(member.getId());
                    return post;
                })
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_POST);
                });
    }


    @Transactional(readOnly = true)
    public PostDetailResponse findById(Long id) {
        return postRepository.findById(id)
                .map(postConverter::toPostDetailResponse)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_POST);
                });
    }

    @Transactional(readOnly = true)
    public List<PostDetailResponse> findAll() {
        return postRepository.findAll()
                .stream()
                .map(postConverter::toPostDetailResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteByEmail(Long id, Email email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });
        Post post = postRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_POST);
                });
        
        // 아래도 결국 비즈니스로직 -> 도메인에서 ㄲ
        // 밑에 없어도 될듯..---> 이것도 post안에 있는게 좋을듯, 연관관계를 가져갈 거면, 엔티티 그래프 타고 가도록 ㄲ하는게
        if (!member.getPosts().ownPost(post)) {
            throw new NotFoundException(ErrorMessage.NOT_EXIST_POST);
        }
        // 엔티티에서 post를 찾는 로직을 추가하자!! 여기서 post를 찾을 필요 xxx
        member.getPosts().deletePost(post);
    }
}
