package com.prgrms.boardjpa.application.post;

import org.springframework.stereotype.Component;

import com.prgrms.boardjpa.application.post.model.Post;
import com.prgrms.boardjpa.application.user.model.User;

@Component
public class PostConverter {

	public Post createRequest2Entity(User writer, PostDto.CreatePostRequest request) {
		return Post.builder()
			.writer(writer)
			.content(request.content())
			.title(request.title())
			.build();
	}

	public PostDto.PostInfo entity2CreateResponse(Post post) {
		return new PostDto.PostInfo(post.getTitle(),
			post.getContent(),
			post.getCreatedBy(),
			0);
	}

	public PostDto.PostInfo entity2Info(Post post, User user) { // 현재 사용자
		return new PostDto.PostInfo(post.getTitle(),
			post.getContent(),
			post.getCreatedBy(),
			post.getLikeCount());
	}

	// 현재 사용자가 "좋아요" 했던 게시글인지 entity 에는 두지 않고, DTO 에만 두려고 했었는데 -> 이렇게 되면 JPA 트랜잭션 영역이 Converter 까지 가게 된다.. 이렇게 되어도 괜찮을까?
	// private boolean isLikedBy(Post post, User user) {
	// 	return post.getLikes().stream()
	// 		.anyMatch(like ->
	// 			like.getUser().isSameUser(user));
	// }
}
