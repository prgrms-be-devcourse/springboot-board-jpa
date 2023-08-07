package devcource.hihi.boardjpa.service;

import devcource.hihi.boardjpa.domain.User;
import devcource.hihi.boardjpa.dto.user.ResponseUserDto;
import devcource.hihi.boardjpa.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceBasicTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Test
    @DisplayName("고객을 저장한다.")
    public void saveTest() {
        //given
        User user = User.builder()
                .name("test")
                .age(24)
                .hobby("party")
                .build();

        //when
        ResponseUserDto dto = userService.createUser(User.toDtoForCreate(user));

        //then
        assertEquals(user.getName(), dto.name());
    }

    @Test
    @DisplayName("id로 고객을 조회한다.")
    public void findById() {
        //given
        User user = User.builder()
                .name("hello")
                .age(34)
                .hobby("reading")
                .build();

        //when
        ResponseUserDto dto = userService.createUser(User.toDtoForCreate(user));
        ResponseUserDto byId = userService.getUser(dto.id());

        //then
        assertEquals(dto.id(), byId.id());
    }
}
