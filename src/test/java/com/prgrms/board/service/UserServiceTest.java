package com.prgrms.board.service;

import com.prgrms.board.domain.Users;
import com.prgrms.board.dto.user.UserResponse;
import com.prgrms.board.dto.user.UserSaveRequest;
import com.prgrms.board.dto.user.UserUpdateRequest;
import com.prgrms.board.global.exception.custom.DuplicateEmailException;
import com.prgrms.board.repository.UserRepository;
import org.junit.jupiter.api.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @DisplayName("중복된 이메일로 사용자 생성 요청 시, DuplicateEmailException 발생")
    @Test
    void NotCreateUser() {
        // Given
        UserSaveRequest saveRequest = new UserSaveRequest("test@test.com", "테스트 사용자", 30);
        when(userRepository.existsByEmail(saveRequest.getEmail())).thenReturn(true);

        // When & Then
        assertThrows(DuplicateEmailException.class, () -> userService.createUser(saveRequest));
    }

    @DisplayName("유효한 이메일로 사용자 조회 시, 해당 사용자 정보를 반환")
    @Test
    void findByEmail() {
        // Given
        String email = "test@test.com";
        Users user = new Users(email, "lee", 30);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        // When
        UserResponse result = userService.findByEmail(email);
        // Then
        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @DisplayName("존재하지 않는 이메일로 사용자 조회 시, NoSuchElementException 발생")
    @Test
    void NotFoundByEmail() {
        // Given
        String email = "test@test.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        // When & Then
        assertThrows(NoSuchElementException.class, () -> userService.findByEmail(email));
    }

    @DisplayName("유효한 사용자 정보로 사용자 수정 시, 업데이트된 사용자 정보를 반환")
    @Test
    void updateUser() {
        // Given
        Long userId = 1L;
        UserUpdateRequest updateRequest = new UserUpdateRequest("업데이트된 사용자", 35);
        Users user = new Users( "test@test.com", "테스트 사용자", 30);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(Users.class))).thenReturn(user);
        // When
        UserResponse result = userService.updateUser(updateRequest, userId);
        // Then
        assertNotNull(result);
        assertEquals(updateRequest.getName(), result.getName());
        assertEquals(updateRequest.getAge(), result.getAge());
    }

    @Test
    @DisplayName("유저 삭제")
    void deletePost() {
        // Given
        long userId = 1L;
        // When
        userService.deleteUserById(userId);
        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }

    // DTO 유효성 테스트 //
    @DisplayName("유효한 UserSaveRequest 객체 생성")
    @Test
    void createValidUserSaveRequest() {
        // Given
        String email = "test@test.com";
        String name = "테스트사용자";
        int age = 30;
        // When
        UserSaveRequest request = new UserSaveRequest(email, name, age);
        // Then
        assertThat(request).isNotNull();
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(request);
        assertThat(violations).isEmpty();
    }

    @DisplayName("유효하지 않은 UserSaveRequest 객체 생성 - null 값")
    @Test
    void createInvalidUserSaveRequest_NullValues() {
        // Given
        String email = null;
        String name = null;
        int age = -1;
        // When
        UserSaveRequest request = new UserSaveRequest(email, name, age);
        // Then
        assertThat(request).isNotNull();
        // Validate constraints
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(request);
        assertThat(violations).hasSize(3);
    }

    @DisplayName("유효하지 않은 UserSaveRequest 객체 생성 - 빈 값")
    @Test
    void createInvalidUserSaveRequest_EmptyValues() {
        // Given
        String email = "";
        String name = "";
        int age = -10;
        // When
        UserSaveRequest request = new UserSaveRequest(email, name, age);
        // Then
        assertThat(request).isNotNull();
        Set<ConstraintViolation<UserSaveRequest>> violations = validator.validate(request);
        assertThat(violations).hasSize(3);
    }
}