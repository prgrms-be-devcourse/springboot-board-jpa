package com.kdt.board.domain.service;

import com.kdt.board.domain.converter.Converter;
import com.kdt.board.domain.dto.UserDto;
import com.kdt.board.domain.model.User;
import com.kdt.board.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceMockTest {
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Converter converter;


    User user;

    @BeforeEach
    void setUp() {
        String testName = "이용훈";
        String testHobby = "Tennis";
        int testAge = 26;

        user = User.builder()
                .id(1L)
                .age(testAge)
                .name(testName)
                .hobby(testHobby)
                .build();
    }

    @Test
    @DisplayName("유저 등록 확인")
    void saveUserTest() {
        // given
        String testName = "이용훈";
        String testHobby = "Tennis";
        int testAge = 26;

        UserDto.SaveRequest userRequest = new UserDto.SaveRequest(testName, testAge, testHobby);
        doReturn(user).when(converter).convertUser(userRequest);
        doReturn(user).when(userRepository).save(any(User.class));

        // when
        Long savedId = userService.save(userRequest);

        // then
        verify(userRepository, times(1)).save(any(User.class));
        verify(converter, times(1)).convertUser(any(UserDto.SaveRequest.class));
        assertThat(savedId).isEqualTo(1L);
    }

    @Test
    @DisplayName("유저 수정 확인")
    void updateUserTest() {
        // given
        String testName = "이용훈";
        String testHobby = "Tennis";
        int testAge = 26;

        UserDto.UpdateRequest userUpdate = new UserDto.UpdateRequest(1L, testName, testAge, testHobby);
        doReturn(Optional.of(user)).when(userRepository).findById(userUpdate.id());

        UserDto.Response userResponse = new UserDto.Response(1L, testName, testAge, testHobby, LocalDateTime.now(), LocalDateTime.now());
        doReturn(userResponse).when(converter).convertUserDto(user);

        // when
        UserDto.Response updatedUserDto = userService.update(userUpdate);

        // then
        assertThat(updatedUserDto.id()).isEqualTo(1L);
        assertThat(updatedUserDto.name()).isEqualTo(testName);
        assertThat(updatedUserDto.age()).isEqualTo(testAge);
        assertThat(updatedUserDto.hobby()).isEqualTo(testHobby);
    }

}
