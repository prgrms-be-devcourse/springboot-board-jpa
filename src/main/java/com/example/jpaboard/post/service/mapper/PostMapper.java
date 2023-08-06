package com.example.jpaboard.post.service.mapper;

import com.example.jpaboard.member.domain.Member;
import com.example.jpaboard.member.service.dto.FindMemberResponse;
import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.service.dto.SaveRequest;

import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    private PostMapper() {
    }

    public Post to(SaveRequest request, FindMemberResponse memberResponse) {
        Member member = new Member(memberResponse.getId(), memberResponse.getName(),
                memberResponse.getAge(), memberResponse.getHobby());
        return new Post(request.title(),
                request.content(),
                member);
    }

}
