package com.kdt.board.domain.service;

import com.kdt.board.domain.dto.PostDto;
import com.kdt.board.domain.dto.UserDto;
import com.kdt.board.domain.repository.PostRepository;
import com.kdt.board.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    Long savedUserId;
    Long savedPostId;

    @AfterEach
    void tearDown() {
        postRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @BeforeEach
    public void saveTest() throws Exception {
        // given
        UserDto.SaveRequest userDto = new UserDto.SaveRequest("YongHoon", 26, "tennis");
        savedUserId = userService.save(userDto);

        PostDto.SaveRequest postDto = new PostDto.SaveRequest("제목테스트", "내용내용내용내용", savedUserId);

        // when
        savedPostId = postService.save(postDto);

        // then
        assertThat(postRepository.count()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Id 값으로 User Entity 를 찾아온다.")
    public void findByIdTest() throws Exception {
        // given

        // when
        UserDto.Response findUserDto = userService.findById(savedUserId);

        // then
        assertThat(findUserDto.name()).isEqualTo("YongHoon");
        assertThat(findUserDto.age()).isEqualTo(26);
        assertThat(findUserDto.hobby()).isEqualTo("tennis");
    }

    @Test
    @DisplayName("모든(All) User Entity 를 찾아온다.")
    public void findAllTest() throws Exception {
        // given
        PageRequest page = PageRequest.of(0, 10);

        // when
        Page<UserDto.Response> all = userService.findAll(page);

        // then
        assertThat(all.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("Id 값으로 User Entity 를 삭제한다.")
    public void deleteByIdTest() throws Exception {
        // given

        // when
        userService.deleteById(savedUserId);

        // then
        assertThat(userService.count()).isEqualTo(0L);
    }


    @Test
    @DisplayName("User Entity 를 update 한다.")
    public void updateTest() throws Exception {
        // given
        UserDto.UpdateRequest changedDto = new UserDto.UpdateRequest(savedUserId, "바뀐 이름", 100, "바뀐 운동");

        // when
        userService.update(changedDto);
        UserDto.Response updatedDto = userService.findById(savedUserId);

        // then
        assertThat(updatedDto.name()).isEqualTo("바뀐 이름");
        assertThat(updatedDto.age()).isEqualTo(100);
        assertThat(updatedDto.hobby()).isEqualTo("바뀐 운동");
    }

}