package com.programmers.springboard.response;

import com.programmers.springboard.entity.Member;
import com.programmers.springboard.entity.Post;


public record PostResponse(Long postId, String title, String content, Long memberId, String memberName) {
	public static PostResponse of(Post post) {
		Member member = post.getMember();
		return new PostResponse(post.getId(), post.getTitle(), post.getContent(), member.getId(), member.getName());
	}
}
