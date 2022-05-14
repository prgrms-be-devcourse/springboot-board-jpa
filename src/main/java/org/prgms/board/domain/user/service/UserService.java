package org.prgms.board.domain.user.service;

import org.prgms.board.domain.user.dto.UserDto;

public interface UserService {

	/** 사용자 등록 **/
	UserDto.Response registerUser(UserDto.Register registerDto);

	/** 사용자 수정 **/
	void updateUser(UserDto.Update updateDto);

	/** 사용자 삭제 **/
	void deleteUser(Long userId);
}
