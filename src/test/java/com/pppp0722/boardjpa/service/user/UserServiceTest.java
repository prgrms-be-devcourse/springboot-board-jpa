package com.pppp0722.boardjpa.service.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.pppp0722.boardjpa.web.dto.UserRequestDto;
import com.pppp0722.boardjpa.web.dto.UserResponseDto;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Slf4j
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void 사용자_save_테스트() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserResponseDto userResponseDto = userService.save(userRequestDto);
        log.info("생성일 : {}, id : {}", userResponseDto.getCreatedAt(), userResponseDto.getId());
        assertThat(userResponseDto.getName(), is(userRequestDto.getName()));
        assertThat(userResponseDto.getAge(), is(userRequestDto.getAge()));
        assertThat(userResponseDto.getHobby(), is(userRequestDto.getHobby()));
    }

    @Test
    void 사용자_findById_테스트() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserResponseDto userResponseDto = userService.save(userRequestDto);

        UserResponseDto foundUser = userService.findById(userResponseDto.getId());

        log.info("{}, {}, {}, {}, {}", foundUser.getCreatedAt(), foundUser.getId(),
            foundUser.getName(), foundUser.getAge(), foundUser.getHobby());
        assertThat(foundUser.getName(), is(userResponseDto.getName()));
        assertThat(foundUser.getAge(), is(userResponseDto.getAge()));
        assertThat(foundUser.getHobby(), is(userResponseDto.getHobby()));
    }

    @Test
    void 사용자_findAll_테스트() {
        UserRequestDto userRequestDto1 = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserRequestDto userRequestDto2 = UserRequestDto.builder()
            .name("Hwanil Kim")
            .age(72)
            .hobby("Study")
            .build();

        userService.save(userRequestDto1);
        userService.save(userRequestDto2);

        Pageable pageable = PageRequest.of(0, 5);
        Page<UserResponseDto> userResponseDtos = userService.findAll(pageable);

        assertThat(userResponseDtos.getTotalElements(), is(2L));
    }

    @Test
    void 사용자_update_테스트() {
        UserRequestDto userCreateRequestDto = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserResponseDto userResponseDto = userService.save(userCreateRequestDto);

        UserRequestDto UserUpdateRequestDto = UserRequestDto.builder()
            .name("Hwanil Kim")
            .age(72)
            .hobby("Study")
            .build();

        UserResponseDto updatedUserDto = userService.update(userResponseDto.getId(),
            UserUpdateRequestDto);

        log.info("{}, {}, {}", updatedUserDto.getName(), updatedUserDto.getAge(),
            updatedUserDto.getHobby());
        assertThat(updatedUserDto.getName(), is(not(userResponseDto.getName())));
        assertThat(updatedUserDto.getAge(), is(not(userResponseDto.getAge())));
        assertThat(updatedUserDto.getHobby(), is(not(userResponseDto.getHobby())));
    }

    @Test
    void 사용자_deleteById_테스트() {
        assertThrows(EntityNotFoundException.class, () -> {
            UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("Ilhwan Lee")
                .age(27)
                .hobby("Game")
                .build();

            UserResponseDto userResponseDto = userService.save(userRequestDto);
            userService.deleteById(userResponseDto.getId());

            // expected = EntityNotFoundException.class
            userService.findById(userResponseDto.getId());
        });
    }
}