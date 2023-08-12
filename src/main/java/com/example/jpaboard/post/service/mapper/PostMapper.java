package com.example.jpaboard.post.service.mapper;

import com.example.jpaboard.member.domain.Member;
import com.example.jpaboard.member.domain.Name;
import com.example.jpaboard.member.service.dto.MemberFindResponse;
import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.service.dto.PostSaveRequest;

import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    private PostMapper() { }

    public Post to(PostSaveRequest request, MemberFindResponse memberResponse) {
        Member member = new Member(memberResponse.getId(), memberResponse.getName(),
                memberResponse.getAge(), memberResponse.getHobby());
        return Post.create(request.title(),
                request.content(),
                member);
    }

}
