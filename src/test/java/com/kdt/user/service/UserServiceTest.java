package com.kdt.user.service;

import com.kdt.post.dto.PostControlRequestDto;
import com.kdt.post.dto.PostDto;
import com.kdt.post.service.PostService;
import com.kdt.user.dto.UserDto;
import com.kdt.user.repository.UserRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Slf4j
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService postService;

    private Long userId;
    private Long postId;
    private UserDto userDto;
    private PostDto postDto;

    @BeforeEach
    void setUp() throws NotFoundException {
        userDto = UserDto.builder()
                .name("son")
                .age(30)
                .hobby("soccer")
                .build();
        userId = userService.save(userDto);

        postDto = PostDto.builder()
                .title("test-title")
                .content("this is a sample post")
                .build();

        PostControlRequestDto postSaveRequestDto = PostControlRequestDto.builder()
                .userId(userId)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();

        postId = postService.save(postSaveRequestDto);
    }

    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자가 정상적으로 조회되는지 확인한다.")
    void findTest() throws NotFoundException {
        //Given
        //When
        UserDto user = userService.find(userId);
        PostDto post = postService.find(postId);

        //Then
        assertThat(user, samePropertyValuesAs(userDto, "id", "postDtos", "createdAt", "createdBy", "lastUpdatedAt"));
        assertThat(user.getId(), is(userId));
        assertThat(user.getPostDtos().size(), is(1));
        assertThat(user.getPostDtos().get(0), samePropertyValuesAs(post, "userDto"));
        log.info(user.toString());
    }

    @Test
    @DisplayName("가입되지 않은 사용자 조회를 요청하면 예외가 발생한다")
    void findNonExistingUserTest() throws NotFoundException {
        //Given
        //When
        //Then
        assertThrows(NotFoundException.class, () -> userService.find(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("사용자 정보가 정상적으로 수정되는지 확인한다.")
    void updateTest() throws NotFoundException {
        //Given
        UserDto user = userService.find(userId);

        //When
        //sleep for checking lastUpdatedAt
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        user.setName("julie");
        user.setHobby("planting");
        userService.update(userId, user);

        //Then
        UserDto savedUser = userService.find(userId);
        assertThat(savedUser, samePropertyValuesAs(user, "lastUpdatedAt", "postDtos"));
        log.info(savedUser.toString());
    }

    @Test
    @DisplayName("사용자가 정상적으로 삭제되는지 확인한다.")
    void deleteTest() throws NotFoundException {
        //Given
        //When
        userService.delete(userId);

        //Then
        assertThrows(NotFoundException.class, () -> userService.find(userId));
    }

    @Test
    @DisplayName("가입하지 않은 사용자를 삭제하면 예외가 발생한다.")
    void deleteInvalidUserTest(){
        //Given
        //When
        //Then
        assertThrows(NotFoundException.class, () -> userService.delete(Long.MAX_VALUE));
    }
}