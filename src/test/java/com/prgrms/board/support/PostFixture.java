package com.prgrms.board.support;

import com.prgrms.board.domain.post.entity.Post;
import com.prgrms.board.domain.user.entity.User;

public class PostFixture {

	public static Post create(User user, String title, String content) {
		return Post.create(user, title, content);
	}
}
