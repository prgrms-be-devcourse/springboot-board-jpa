package com.devcourse.springbootboard.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devcourse.springbootboard.user.converter.UserConverter;
import com.devcourse.springbootboard.user.domain.Hobby;
import com.devcourse.springbootboard.user.domain.User;
import com.devcourse.springbootboard.user.dto.UserDeleteResponse;
import com.devcourse.springbootboard.user.dto.UserResponse;
import com.devcourse.springbootboard.user.dto.UserSignUpRequest;
import com.devcourse.springbootboard.user.dto.UserUpdateRequest;
import com.devcourse.springbootboard.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	private UserRepository userRepository;

	@Mock
	private UserConverter userConverter;

	@InjectMocks
	private UserService userService;

	@DisplayName("회원가입 테스트")
	@Test
	void testSignUp() {
		// given
		User stubUser = new User(1L, "Samkimuel", 29, new Hobby("축구"));
		given(userConverter.convertUserSignUpRequest(any(UserSignUpRequest.class))).willReturn(stubUser);
		UserResponse stubUserResponse = new UserResponse(1L, "Samkimuel", 29, new Hobby("축구"));
		given(userConverter.toUserResponse(userRepository.save(any(User.class)))).willReturn(stubUserResponse);

		// when
		UserSignUpRequest userSignUpRequest = new UserSignUpRequest("Samkimuel", 29, "축구");
		UserResponse userResponse = userService.saveUser(userSignUpRequest);

		// then
		assertThat(userResponse).isNotNull()
			.hasFieldOrPropertyWithValue("id", 1L)
			.hasFieldOrPropertyWithValue("name", "Samkimuel")
			.hasFieldOrPropertyWithValue("age", 29)
			.hasFieldOrPropertyWithValue("Hobby", new Hobby("축구"));
	}

	@DisplayName("회원 정보 조회 테스트")
	@Test
	void testFindUser() {
		// given
		User stubUser = new User(1L, "Samkimuel", 29, new Hobby("축구"));
		given(userRepository.findById(anyLong())).willReturn(Optional.of(stubUser));
		UserResponse stubUserResponse = new UserResponse(1L, "Samkimuel", 29, new Hobby("축구"));
		given(userConverter.toUserResponse(stubUser)).willReturn(stubUserResponse);

		// when
		UserResponse userResponse = userService.findUser(1L);

		// then
		assertThat(userResponse).isNotNull()
			.hasFieldOrPropertyWithValue("id", 1L)
			.hasFieldOrPropertyWithValue("name", "Samkimuel")
			.hasFieldOrPropertyWithValue("age", 29)
			.hasFieldOrPropertyWithValue("hobby", new Hobby("축구"));
	}

	@DisplayName("회원 정보 수정 테스트")
	@Test
	void testUpdateUser() {
		// given
		User stubUser = new User(1L, "Samkimuel", 29, new Hobby("축구"));
		given(userRepository.findById(anyLong())).willReturn(Optional.of(stubUser));
		UserResponse stubUserResponse = new UserResponse(1L, "명환", 29, new Hobby("풋살"));
		given(userConverter.toUserResponse(stubUser)).willReturn(stubUserResponse);

		// when
		UserUpdateRequest userUpdateRequest = new UserUpdateRequest("명환", 29, "풋살");
		UserResponse userResponse = userService.updateUser(1L, userUpdateRequest);

		// then
		assertThat(userResponse).isNotNull()
			.hasFieldOrPropertyWithValue("id", 1L)
			.hasFieldOrPropertyWithValue("name", "명환")
			.hasFieldOrPropertyWithValue("age", 29)
			.hasFieldOrPropertyWithValue("hobby", new Hobby("풋살"));
	}

	@DisplayName("회원 탈퇴 테스트")
	@Test
	void testDeleteUser() {
		// given
		User stubUser = new User(1L, "Samkimuel", 29, new Hobby("축구"));
		given(userRepository.findById(anyLong())).willReturn(Optional.of(stubUser));
		given(userConverter.toDeleteResponse(anyLong())).willReturn(new UserDeleteResponse(1L));

		// when
		UserDeleteResponse deleteResponse = userService.deleteUser(1L);

		// then
		verify(userRepository, times(1)).delete(stubUser);
		assertThat(deleteResponse.getId()).isEqualTo(1L);
	}
}
