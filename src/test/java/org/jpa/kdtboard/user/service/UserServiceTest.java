package org.jpa.kdtboard.user.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jpa.kdtboard.domain.board.UserRepository;
import org.jpa.kdtboard.user.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by yunyun on 2021/10/14.
 */

@Slf4j
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("사용자 정보를 저장할 수 있다.")
    void saveTest() throws NotFoundException {
        //Given
        var criteriaName = "홍길동";
        var criteriaAge = 100;
        var criteriaHobby = "코딩";

        //When
        var userIdSaved = userService.save(UserDto.builder()
                .name(criteriaName)
                .createdBy("관리자")
                .age(criteriaAge)
                .hobby(criteriaHobby)
                .build());

        //Then
        var dataFindById = userRepository.findById(userIdSaved).orElseThrow(() -> new NotFoundException("해당 Id에 관련된 데이터를 찾지 못하였습니다."));

        assertThat(dataFindById.getName(), is(criteriaName));
        assertThat(dataFindById.getAge(), is(criteriaAge));
        assertThat(dataFindById.getHobby(), is(criteriaHobby));
    }
}