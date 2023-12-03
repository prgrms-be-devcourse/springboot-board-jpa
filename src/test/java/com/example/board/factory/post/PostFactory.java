package com.example.board.factory.post;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.post.entity.Post;

import static com.example.board.factory.member.MemberFactory.createMemberWithRoleUser;

public class PostFactory {

    public static Post createPost() {
        return Post.builder()
            .id(1L)
            .title("게시글 1번")
            .content("게시글 1번 내용입니다.")
            .member(createMemberWithRoleUser())
            .build();
    }

    public static Post createPostWithMember(Member member) {
        return Post.builder()
            .id(1L)
            .title("게시글 1번")
            .content("게시글 1번 내용입니다.")
            .member(member)
            .build();
    }
}
