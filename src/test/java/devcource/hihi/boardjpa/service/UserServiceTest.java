package devcource.hihi.boardjpa.service;

import devcource.hihi.boardjpa.domain.User;
import devcource.hihi.boardjpa.dto.user.ResponseUserDto;
import devcource.hihi.boardjpa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    @Test
    public void saveTest() {
        //given
        User user = User.builder()
                .name("test")
                .age(24)
                .hobby("party")
                .build();

        //when
        ResponseUserDto dto = userService.createDto(User.toCreateDto(user));

        //then
        assertEquals(user.getName(), dto.name());
    }

    @Test
    public void findById(){
        //given
        User user = User.builder()
                .name("hello")
                .age(34)
                .hobby("reading")
                .build();

        //when
        ResponseUserDto dto = userService.createDto(User.toCreateDto(user));
        ResponseUserDto byId = userService.findById(dto.id());

        //then
        assertEquals(dto.id(),byId.id());
    }

}