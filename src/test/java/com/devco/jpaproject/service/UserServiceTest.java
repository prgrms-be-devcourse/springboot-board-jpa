package com.devco.jpaproject.service;

import com.devco.jpaproject.controller.dto.UserRequestDto;
import com.devco.jpaproject.controller.dto.UserResponseDto;
import com.devco.jpaproject.domain.User;
import com.devco.jpaproject.repository.UserRepository;
import com.devco.jpaproject.service.converter.Converter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Converter converter;

    @Mock
    UserResponseDto responseDto;

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("jihun")
                .age(12)
                .hobby("hobby1")
                .build();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("유저를 삽입할 수 있다.")
    void insertTest() {
        //given
        var dto = UserRequestDto.builder()
                .age(122)
                .name("jihun-dto")
                .hobby("hobby1-test")
                .build();

        //when
        when(converter.toUserEntity(dto)).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
        var savedUserId = userService.insert(dto);

        //then
        assertThat(savedUserId).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("db에 저장된 사용자 id로 단건 정보를 찾을 수 있다.")
    void findByIdTest() throws Exception {
        //given
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(converter.toUserResponseDto(user)).willReturn(responseDto);

        //when
        UserResponseDto dto = userService.findById(1L);

        //then
        then(userRepository).should().findById(1L);
        then(converter).should().toUserResponseDto(user);

        assertThat(dto).isNotNull();
        log.info("saved User name: {}, dto user name: {}", user.getName(), dto.getName());
    }

}