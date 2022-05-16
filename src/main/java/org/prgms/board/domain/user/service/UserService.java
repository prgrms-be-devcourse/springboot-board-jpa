package org.prgms.board.domain.user.service;

import org.prgms.board.domain.user.domain.User;

public interface UserService {

	/** 사용자 등록 **/
	User saveUser(String name, int age);

	/** 사용자 조회 **/
	User findUser(Long userId);

	/** 사용자 삭제 **/
	void deleteUser(Long userId);


	/** 사용자 수정 **/
	void updateUser(String name, int age, Long userId);

}
