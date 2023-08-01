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
    UserRepository userRepository;

    @InjectMocks
    UserService userService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsersWithPagination() {
        // 가짜 데이터 생성
        List<User> sampleUsers = UserRepositoryTestHelper.createSampleUsers(10);

        // Mockito를 사용하여 userRepository.findAll() 호출 시 가짜 데이터 반환하도록 설정
        Pageable pageable = PageRequest.of(0, 5); // 0번째 페이지, 페이지 당 5개 항목
        Page<User> samplePage = new PageImpl<>(sampleUsers.subList(0, 5), pageable, sampleUsers.size());
        when(userRepository.findAll(pageable)).thenReturn(samplePage);

        // getUsersWithPagination 메서드 호출
        Page<User> resultPage = userService.getUsersWithPagination(0, 5);

        // 결과 검증
        assertEquals(samplePage, resultPage);
        assertEquals(5, resultPage.getNumberOfElements()); // 페이지당 5개 항목 반환
        assertEquals(sampleUsers.size(), resultPage.getTotalElements()); // 총 항목 수 검증
    }

}