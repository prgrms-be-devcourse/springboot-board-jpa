package com.prgrms.board.service.converter;

import com.prgrms.board.domain.Member;
import com.prgrms.board.domain.Post;
import com.prgrms.board.dto.request.MemberCreateDto;
import com.prgrms.board.dto.response.MemberResponseDto;
import com.prgrms.board.dto.request.PostCreateDto;
import com.prgrms.board.dto.response.PostResponseDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EntityConverter {
    public Member createMemberFromDto(MemberCreateDto dto) {
        return Member.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .hobby(dto.getHobby())
                .build();
    }

    public MemberResponseDto memberEntityToDto(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .age(member.getAge())
                .hobby(member.getHobby())
                .posts(member.getPosts().stream().map(this::PostEntityToDto).collect(Collectors.toList()))
                .build();

    }

    public PostResponseDto PostEntityToDto(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writerId(post.getWriter().getId())
                .build();
    }

    public Post createPostFromDto(PostCreateDto createDto) {
        return Post.builder()
                .title(createDto.getTitle())
                .content(createDto.getContent())
                .build();
    }
}
