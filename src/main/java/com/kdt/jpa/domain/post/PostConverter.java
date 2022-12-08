package com.kdt.jpa.domain.post;

import org.springframework.stereotype.Component;

import com.kdt.jpa.domain.member.MemberConverter;
import com.kdt.jpa.domain.member.dto.MemberResponse;
import com.kdt.jpa.domain.member.model.Member;
import com.kdt.jpa.domain.post.dto.PostRequest;
import com.kdt.jpa.domain.post.dto.PostResponse;
import com.kdt.jpa.domain.post.model.Post;

@Component
public class PostConverter {

	private final MemberConverter memberConverter;

	public PostConverter(MemberConverter memberConverter) {
		this.memberConverter = memberConverter;
	}

	public Post toEntity(PostRequest.WritePostRequest writePostRequest, MemberResponse memberResponse) {
		Member member = this.memberConverter.toEntity(memberResponse);

		return Post.createNew(writePostRequest.title(), writePostRequest.content(), member);
	}

	public PostResponse toPostResponse(Post post) {
		return new PostResponse
			(
				post.getId(),
				post.getTitle(),
				post.getContent(),
				this.memberConverter.toMemberResponse(post.getAuthor()),
				post.getCreatedAt()
			);
	}

	public PostResponse.UpdatePostResponse toUpdatePostResponse(Post post) {
		return new PostResponse.UpdatePostResponse(post.getTitle(), post.getContent());
	}

	public PostResponse.WritePostResponse toWritePostResponse(Post post) {
		return new PostResponse.WritePostResponse(post.getId());
	}

}
