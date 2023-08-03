package com.example.jpaboard.post.service.mapper;

import com.example.jpaboard.member.domain.Member;
import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.service.dto.SaveRequest;
import com.example.jpaboard.post.service.dto.SaveResponse;
import com.example.jpaboard.post.service.dto.UpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private PostMapper() { }

    public Post to(SaveRequest request, Member member){
        return new Post(request.title(),
                        request.content(),
                        member);
    }
}
