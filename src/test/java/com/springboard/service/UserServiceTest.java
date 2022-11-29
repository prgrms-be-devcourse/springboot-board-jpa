package com.springboard.service;

import com.springboard.common.exception.FindFailException;
import com.springboard.user.dto.*;
import com.springboard.user.repository.UserRepository;
import com.springboard.user.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("User Service 테스트")
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Nested
    @DisplayName("findOne() : 사용자 조회 테스트")
    class FindOneTest {
        List<UserRequest> expectedList = new ArrayList<>();

        @BeforeEach
        void setUp() {
            expectedList.add(new UserRequest("kate", 15, "플룻"));
            expectedList.add(new UserRequest("moosong", 25, "비올라"));
        }

        @Test
        @DisplayName("성공 : 존재하는 PK 값으로 사용자를 조회한 경우")
        void success() {
            // Given
            List<UserResponse> actualList = new ArrayList<>();
            expectedList.forEach(request -> actualList.add(userService.save(request)));
            // When, Then
            actualList.forEach(userResponse -> {
                UserResponse response = userService.findOne(userResponse.id());
                assertThat(response.name()).isEqualTo(userResponse.name());
                assertThat(response.age()).isEqualTo(userResponse.age());
                assertThat(response.hobby()).isEqualTo(userResponse.hobby());
                assertThat(response.createdAt()).isNotNull();
                assertThat(response.updatedAt()).isNotNull();
            });
        }

        @Test
        @DisplayName("실패 : 존재하지 않는 PK 값으로 사용자를 조회한 경우")
        void fail() {
            // Given, When
            Throwable response = catchThrowable(() -> userService.findOne(0L));
            // Then
            assertThat(response).isInstanceOf(FindFailException.class);
        }
    }

    @Nested
    @DisplayName("findAll() : 사용자 목록 조회 테스트")
    class FindAllTest {
        List<UserRequest> expectedList = new ArrayList<>();

        @BeforeEach
        void setUp() {
            expectedList.add(new UserRequest("moosong", 25, "비올라"));
            expectedList.add(new UserRequest("kate", 15, "플룻"));
        }

        @Test
        @DisplayName("성공 : 저장된 사용자가 있는 경우 페이지 요청에 따른 반환")
        void success() {
            // Given
            expectedList.forEach(request -> userService.save(request));
            // When
            PageRequest page = PageRequest.of(0, expectedList.size());
            Page<UserResponse> actualList = userService.findAll(page);
            // Then
            assertThat(actualList.getTotalElements()).isEqualTo(expectedList.size());
        }

        @Test
        @DisplayName("실패 : 저장된 사용자가 없는 경우 빈 페이지를 반환")
        void fail() {
            // Given, When
            PageRequest page = PageRequest.of(0, expectedList.size());
            Page<UserResponse> actualList = userService.findAll(page);
            // Then
            assertThat(actualList.get().count()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("save() : 사용자 저장 테스트")
    class SaveTest {
        @Test
        @DisplayName("성공 : 사용자 생성 DTO에 모든 값이 채워진 경우")
        void success() {
            // Given
            UserRequest request = new UserRequest("moosong", 25, "비올라");
            // When
            UserResponse expectedResponse = userService.save(request);
            UserResponse actualResponse = userService.findOne(expectedResponse.id());
            // Then
            assertThat(actualResponse.name()).isEqualTo(expectedResponse.name());
            assertThat(actualResponse.age()).isEqualTo(expectedResponse.age());
            assertThat(actualResponse.hobby()).isEqualTo(expectedResponse.hobby());
            assertThat(actualResponse.createdAt()).isNotNull();
            assertThat(actualResponse.updatedAt()).isNotNull();
        }

        @Test
        @DisplayName("실패 : 사용자 생성 DTO의 null 비허용 인자가 null인 경우")
        void fail() {
            // Given
            UserRequest request = new UserRequest(null, 25, null);
            // When
            Throwable response = catchThrowable(() -> userService.save(request));
            // Then
            assertThat(response).isInstanceOf(DataIntegrityViolationException.class);
        }
    }

    @Nested
    @DisplayName("updateOne() : 사용자 수정 테스트")
    class UpdateOneTest {
        UserResponse origin;

        @BeforeEach
        void setUp() {
            origin = userService.save(new UserRequest("moosong", 25, "비올라"));
        }

        @Test
        @DisplayName("성공 : 존재하는 PK에 대한 수정을 요정하는 경우")
        void success() {
            // Given
            UserRequest request = new UserRequest("moosong", 15, "플룻");
            // When
            UserResponse expectedResponse = userService.updateOne(origin.id(), request);
            UserResponse actualResponse = userService.findOne(origin.id());
            // Then
            assertThat(actualResponse.age()).isEqualTo(expectedResponse.age());
            assertThat(actualResponse.hobby()).isEqualTo(expectedResponse.hobby());
            assertThat(actualResponse.updatedAt()).isAfter(expectedResponse.updatedAt());
        }

        @Test
        @DisplayName("실패 : 존재하지 않는 PK에 대한 수정을 요정하는 경우")
        void fail() {
            // Given
            UserRequest request = new UserRequest("moosong", 15, "플룻");
            // When
            Throwable response = catchThrowable(() -> userService.updateOne(0L, request));
            // Then
            assertThat(response).isInstanceOf(FindFailException.class);
        }
    }

    @Nested
    @DisplayName("deleteOne() : 사용자 삭제 테스트")
    class DeleteOneTest {
        UserResponse origin;

        @BeforeEach
        void setUp() {
            origin = userService.save(new UserRequest("moosong", 25, "비올라"));
        }

        @Test
        @DisplayName("성공 : 존재하는 PK 값으로 삭제한 경우")
        void success() {
            // Given, When
            userService.deleteOne(origin.id());
            // Then
            Throwable response = catchThrowable(() -> userService.findOne(origin.id()));
            assertThat(response).isInstanceOf(FindFailException.class);
        }

        @Test
        @DisplayName("실패 : 존재하지 않는 PK 값으로 삭제한 경우")
        void fail() {
            // Given, When
            Throwable response = catchThrowable(() -> userService.deleteOne(0L));
            // Then
            assertThat(response).isInstanceOf(EmptyResultDataAccessException.class);
        }
    }
}