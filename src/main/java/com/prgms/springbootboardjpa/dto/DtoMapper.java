package com.prgms.springbootboardjpa.dto;

import com.prgms.springbootboardjpa.model.Member;
import com.prgms.springbootboardjpa.model.Post;

public class DtoMapper {
    public static MemberDto memberToMemberDto(Member member) {
        return new MemberDto(member.getMemberId(), member.getName(), member.getAge(), member.getHobby());
    }

    public static Member memberDtoToMember(MemberDto memberDto) {
        return new Member(memberDto.getMemberId(), memberDto.getName(), memberDto.getAge(), memberDto.getHobby());
    }

    public static Member createMemberRequestToMember(CreateMemberRequest createMemberRequest) {
        return new Member(createMemberRequest.getName(), createMemberRequest.getAge(), createMemberRequest.getHobby());
    }

    public static PostDto postToPostDto(Post post) {
        return new PostDto(post.getPostId(), post.getTitle(), post.getContent());
    }
}
