package com.prgms.springbootboardjpa.facade;

import com.prgms.springbootboardjpa.dto.CreatePostRequest;
import com.prgms.springbootboardjpa.dto.DtoMapper;
import com.prgms.springbootboardjpa.dto.MemberDto;
import com.prgms.springbootboardjpa.service.MemberService;
import com.prgms.springbootboardjpa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class PostFacade {
    private final MemberService memberService;
    private final PostService postService;

    @Transactional
    public Long createPost(CreatePostRequest createPostRequest) {
        MemberDto memberDto = memberService.getMemberById(createPostRequest.getMemberId());
        return postService.createPost(
                DtoMapper.memberDtoToMember(memberDto),
                createPostRequest.getTitle(),
                createPostRequest.getContent()
        );
    }
}
