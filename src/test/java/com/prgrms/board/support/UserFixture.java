package com.prgrms.board.support;

import com.prgrms.board.domain.user.entity.User;

public class UserFixture {

	public static User create() {
		return new User(1L, "test", 10, "post");
	}
}
