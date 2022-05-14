package org.prgms.board.domain.user.service;

import org.prgms.board.domain.user.dto.UserDto;

public interface UserService {

	/** 사용자 등록 **/
	UserDto.Response saveUser(UserDto.Save saveDto);

	/** 사용자 조회 **/
	UserDto.Response getUser(Long userId);

	/** 사용자 삭제 **/
	void updateUser(UserDto.Update updateDto);

	void deleteUser(Long userId);
	/** 사용자 수정 **/

}
