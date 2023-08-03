package devcource.hihi.boardjpa.service;

import devcource.hihi.boardjpa.domain.User;
import devcource.hihi.boardjpa.dto.user.ResponseUserDto;
import devcource.hihi.boardjpa.repository.UserRepository;
import devcource.hihi.boardjpa.test.UserRepositoryTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServicePagenationTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsersWithPagination() {

        //given
        List<User> sampleUsers = UserRepositoryTestHelper.createSampleUsers(10);

        Pageable pageable = PageRequest.of(0, 5);
        Page<User> samplePage = new PageImpl<>(sampleUsers.subList(0, 5), pageable, sampleUsers.size());
        when(userRepository.findAll(pageable)).thenReturn(samplePage);

        // when
        Page<User> resultPage = userService.getUsersWithPagination(0, 5);

        // then
        assertEquals(samplePage, resultPage);
        assertEquals(5, resultPage.getNumberOfElements());
        assertEquals(sampleUsers.size(), resultPage.getTotalElements());
    }

}