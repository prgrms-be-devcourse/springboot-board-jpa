package com.prgrms.springbootboardjpa.post.dto;

import com.prgrms.springbootboardjpa.member.domain.Member;
import com.prgrms.springbootboardjpa.post.domain.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostInsertRequest {
    private Member createdBy;
    private String title;
    private String content;

    public PostInsertRequest() {
    }

    public PostInsertRequest(Member createdBy, String title, String content) {
        this.createdBy = createdBy;
        this.title = title;
        this.content = content;
    }

    public Post toPost() {
        return new Post(this.createdBy, this.title, this.content);
    }


}
