package kdt.cse0518.board.user.service;

import kdt.cse0518.board.user.converter.UserConverter;
import kdt.cse0518.board.user.dto.UserDto;
import kdt.cse0518.board.user.entity.User;
import kdt.cse0518.board.user.factory.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class UserServiceTest {

    private final Pageable pageable = PageRequest.of(0, 5);
    private User newUser1;
    private User newUser2;
    @Autowired
    private UserService service;
    @Autowired
    private UserFactory factory;
    @Autowired
    private UserConverter converter;

    @BeforeEach
    void setUp() {
        final User user1 = factory.createUser("사람1", 25, "취미1");
        newUser1 = service.saveUser(converter.toUserDto(user1));
        final User user2 = factory.createUser("사람2", 26, "취미2");
        newUser2 = service.saveUser(converter.toUserDto(user2));
    }

    @Test
    @DisplayName("모든 User를 조회할 수 있다.")
    @Transactional
    void testFindAll() {
        final List<UserDto> allUser = service.findAll(pageable).getContent();

        assertThat(allUser.size(), is(2));
        assertThat(allUser.get(0).getName(), is("사람1"));
        assertThat(allUser.get(1).getName(), is("사람2"));
    }

    @Test
    @DisplayName("User를 Id로 조회할 수 있다.")
    @Transactional
    void testFindById() {
        assertThat(service.findById(newUser1.getUserId()).getName(), is("사람1"));
        assertThat(service.findById(newUser2.getUserId()).getName(), is("사람2"));
    }

    @Test
    @DisplayName("client의 새로운 User 생성 요청을 처리할 수 있다.")
    @Transactional
    void testNewRequestDtoSave() {
        // Given
        final UserDto requestUserDto = UserDto.builder()
                .name("요청 이름")
                .age(33)
                .hobby("요청 취미")
                .build();

        // When
        service.newRequestDtoSave(requestUserDto);

        // Then
        final List<UserDto> allUserDto = service.findAll(pageable).getContent();
        assertThat(allUserDto.size(), is(3));
        assertThat(allUserDto.get(2).getName(), is("요청 이름"));
    }

    @Test
    @DisplayName("User를 수정할 수 있다.")
    @Transactional
    void testUpdate() {
        // Given
        final UserDto userDto = service.findById(newUser2.getUserId());

        // When
        userDto.update("사람2", 50, "바뀐 취미");
        service.update(userDto);

        // Then
        assertThat(service.findById(newUser2.getUserId()).getHobby(), is("바뀐 취미"));
    }
}