package com.prgrms.dlfdyd96.board.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prgrms.dlfdyd96.board.domain.User;
import com.prgrms.dlfdyd96.board.user.converter.UserConverter;
import com.prgrms.dlfdyd96.board.user.dto.CreateUserRequest;
import com.prgrms.dlfdyd96.board.user.dto.UpdateUserRequest;
import com.prgrms.dlfdyd96.board.user.dto.UserResponse;
import com.prgrms.dlfdyd96.board.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @InjectMocks
  private UserService userService;

  @Mock
  private UserConverter userConverter;

  @Mock
  private UserRepository userRepository;

  private User user;

  @BeforeEach
  void setUp() {
    user = User.builder()
        .id(1L)
        .name("yong")
        .age(11)
        .hobby("running")
        .build();
  }

  @Test
  @DisplayName("사용자를 생성할 수 있다.")
  void testSave() {
    // GIVEN
    CreateUserRequest requestDto = CreateUserRequest.builder()
        .name("yong")
        .age(11)
        .hobby("running")
        .build();

    User stubUser = User.builder()
        .name(requestDto.getName())
        .age(requestDto.getAge())
        .hobby(requestDto.getHobby())
        .posts(Collections.emptyList())
        .build();

    when(userConverter.convertUser(requestDto)).thenReturn(stubUser); // verify ?
    // any() : 어짜피 test를 위한 stub 만드는 거니까.
    when(userRepository.save(any())).thenReturn(user); // verify ? : void 일 때 verify 하는 것.

    // WHEN
    Long userId = userService.save(requestDto);

    // THEN
    assertThat(userId).isEqualTo(user.getId());
  }

  @Test
  @DisplayName("id로 user를 조회할 수 있다.")
  void testFindOne() throws NotFoundException {
    // GIVEN
    UserResponse stubResponse = UserResponse.builder()
        .id(user.getId())
        .age(user.getAge())
        .name(user.getName())
        .hobby(user.getHobby())
        .build();
    Long givenUserId = user.getId();
    when(userRepository.findById(givenUserId)).thenReturn(Optional.of(user));
    when(userConverter.convertUserResponse(any())).thenReturn(stubResponse);

    // WHEN
    UserResponse result = userService.findOne(givenUserId);

    // THEN
    assertThat(result.getId()).isEqualTo(user.getId());
    assertThat(result.getName()).isEqualTo(user.getName());
    assertThat(result.getHobby()).isEqualTo(user.getHobby());
    assertThat(result.getAge()).isEqualTo(user.getAge());

  }

  @Test
  @DisplayName("id로 조회하면 not found exception이 발생한다.")
  void testFindOneException() {
    // GIVEN
    Long givenUserId = 0L;
    when(userRepository.findById(givenUserId)).thenReturn(Optional.empty());

    // WHEN
    // THEN
    assertThatThrownBy(
        () -> this.userService.findOne(givenUserId)
    ).isInstanceOf(NotFoundException.class);

  }

  @Test
  @DisplayName("사용자를 수정할 수 있다.")
  void testUpdate() throws NotFoundException {
    // GIVEN
    User originUser = User.builder()
        .id(1L)
        .name("yong")
        .age(11)
        .hobby("running")
        .build();
    UpdateUserRequest requestDto = UpdateUserRequest.builder()
        .age(22)
        .name("min jung")
        .hobby("티키타카")
        .build();
    UserResponse responseStub = UserResponse.builder()
        .id(originUser.getId())
        .age(requestDto.getAge())
        .name(requestDto.getName())
        .hobby(requestDto.getHobby())
        .build();
    Long givenUserId = originUser.getId();
    when(userRepository.findById(givenUserId)).thenReturn(Optional.of(originUser));
    when(userConverter.convertUserResponse(any())).thenReturn(responseStub);

    // WHEN
    UserResponse retrievedResponseDto = userService.update(givenUserId, requestDto);

    // THEN
    verify(userConverter).convertUserResponse(
        argThat(argUser -> {
              assertThat(argUser.getId()).isEqualTo(originUser.getId());
              assertThat(argUser.getName()).isEqualTo(requestDto.getName());
              assertThat(argUser.getAge()).isEqualTo(requestDto.getAge());
              assertThat(argUser.getHobby()).isEqualTo(requestDto.getHobby());
              return true;
            }
        ));

    assertThat(retrievedResponseDto.getName()).isEqualTo(responseStub.getName());
    assertThat(retrievedResponseDto.getAge()).isEqualTo(responseStub.getAge());
    assertThat(retrievedResponseDto.getHobby()).isEqualTo(responseStub.getHobby());
  }

  @Test
  @DisplayName("update 수행 전 없는 id 를 발견한 경우 not found exception을 뱉는다.")
  void testUpdateException() throws NotFoundException {
    // GIVEN
    Long givenUserId = 0L;
    when(userRepository.findById(givenUserId)).thenReturn(Optional.empty());
    UpdateUserRequest requestDto = UpdateUserRequest.builder()
        .age(22)
        .name("min jung")
        .hobby("티키타카")
        .build();

    // WHEN
    // THEN
    assertThatThrownBy(
        () -> this.userService.update(givenUserId, requestDto)
    ).isInstanceOf(NotFoundException.class);

  }

  @Test
  @DisplayName("User 리스트를 조회할 수 있다.")
  void testFindUsers() {
    // GIVEN
    List<User> users = new ArrayList<>();
    users.add(User.builder()
        .id(1L)
        .name("yong")
        .age(11)
        .hobby("running")
        .build());
    users.add(User.builder()
        .id(2L)
        .name("min jung")
        .age(12)
        .hobby("eating")
        .build());
    List<UserResponse> userResponsesStub = users.stream().map(item -> UserResponse.builder()
        .id(item.getId())
        .name(item.getName())
        .age(item.getAge())
        .hobby(item.getHobby())
        .build()).collect(Collectors.toList());

    Page<User> repositoryPages = new PageImpl<>(users);
    Page<UserResponse> convertedPages = new PageImpl<>(userResponsesStub);
    when(userRepository.findAll(any(Pageable.class))).thenReturn(repositoryPages);
    when(userConverter.convertUserResponse(any())).thenReturn(userResponsesStub.get(0), userResponsesStub.get(1));

    Pageable pageable = PageRequest.of(0, 2);

    // WHEN
    Page<UserResponse> userResponses = userService.findUsers(pageable);

    // THEN
    assertThat(userResponses.getTotalPages()).isEqualTo(convertedPages.getTotalPages());
    assertThat(userResponses.getTotalElements()).isEqualTo(convertedPages.getTotalElements());

  }

  @Test
  @DisplayName("id로 조회를 하고 삭제를 할 수 있다.")
  void testDelete() throws NotFoundException {
    // GIVEN
    User originUser = User.builder()
        .id(1L)
        .name("yong")
        .age(11)
        .hobby("running")
        .build();
    Long givenUserId = originUser.getId();
    when(userRepository.findById(givenUserId)).thenReturn(Optional.of(originUser));

    // WHEN
    userService.delete(originUser.getId());

    // THEN
    verify(userRepository).delete(originUser);
  }

  @Test
  @DisplayName("delete수행 전 없는 id 를 발견한 경우 not found exception을 뱉는다.")
  void testDeleteException() throws NotFoundException {
    // GIVEN
    Long givenUserId = 0L;
    when(userRepository.findById(givenUserId)).thenReturn(Optional.empty());

    // WHEN
    // THEN
    assertThatThrownBy(
        () -> this.userService.findOne(givenUserId)
    ).isInstanceOf(NotFoundException.class);
  }
}