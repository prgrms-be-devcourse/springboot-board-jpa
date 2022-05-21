package com.pppp0722.boardjpa.service.post;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.pppp0722.boardjpa.domain.post.PostRepository;
import com.pppp0722.boardjpa.domain.user.UserRepository;
import com.pppp0722.boardjpa.service.user.UserService;
import com.pppp0722.boardjpa.web.dto.PostRequestDto;
import com.pppp0722.boardjpa.web.dto.PostResponseDto;
import com.pppp0722.boardjpa.web.dto.UserRequestDto;
import com.pppp0722.boardjpa.web.dto.UserResponseDto;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
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
class PostServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 게시글_save_테스트() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserResponseDto userResponseDto = userService.save(userRequestDto);

        PostRequestDto postRequestDto = PostRequestDto.builder()
            .title("Hi")
            .content("Nice to meet you.")
            .userResponseDto(userResponseDto)
            .build();

        PostResponseDto postResponseDto = postService.save(postRequestDto);

        log.info("생성일 : {}, id : {}, 작성자 id : {}, 작성자 name : {}",
            postResponseDto.getCreatedAt(),
            postResponseDto.getId(),
            postResponseDto.getUserResponseDto().getId(),
            postResponseDto.getUserResponseDto().getName()
        );
        assertThat(postResponseDto.getTitle(), is(postRequestDto.getTitle()));
        assertThat(postResponseDto.getContent(), is(postRequestDto.getContent()));
        assertThat(postResponseDto.getUserResponseDto().getId(),
            is(postRequestDto.getUserResponseDto().getId()));
    }

    @Test
    void 게시글_findById_테스트() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserResponseDto userResponseDto = userService.save(userRequestDto);

        PostRequestDto postRequestDto = PostRequestDto.builder()
            .title("Hi")
            .content("Nice to meet you.")
            .userResponseDto(userResponseDto)
            .build();

        PostResponseDto postResponseDto = postService.save(postRequestDto);

        PostResponseDto foundPost = postService.findById(postResponseDto.getId());

        assertThat(foundPost.getTitle(), is(postResponseDto.getTitle()));
        assertThat(foundPost.getContent(), is(postResponseDto.getContent()));
        assertThat(foundPost.getUserResponseDto().getId(),
            is(postResponseDto.getUserResponseDto().getId())
        );
    }

    @Test
    void 게시글_findAll_테스트() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserResponseDto userResponseDto = userService.save(userRequestDto);

        PostRequestDto postRequestDto1 = PostRequestDto.builder()
            .title("Hi")
            .content("Nice to meet you.")
            .userResponseDto(userResponseDto)
            .build();

        PostRequestDto postRequestDto2 = PostRequestDto.builder()
            .title("Bye")
            .content("See you again.")
            .userResponseDto(userResponseDto)
            .build();

        postService.save(postRequestDto1);
        postService.save(postRequestDto2);

        Pageable pageable = PageRequest.of(0, 5);
        Page<PostResponseDto> postResponseDtos = postService.findAll(pageable);

        assertThat(postResponseDtos.getTotalElements(), is(2L));
    }

    @Test
    void 게시글_findByUserId_테스트() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserResponseDto userResponseDto = userService.save(userRequestDto);

        PostRequestDto postRequestDto1 = PostRequestDto.builder()
            .title("Hi")
            .content("Nice to meet you.")
            .userResponseDto(userResponseDto)
            .build();

        PostRequestDto postRequestDto2 = PostRequestDto.builder()
            .title("Bye")
            .content("See you again.")
            .userResponseDto(userResponseDto)
            .build();

        postService.save(postRequestDto1);
        postService.save(postRequestDto2);

        Pageable pageable = PageRequest.of(0, 5);
        Page<PostResponseDto> postResponseDtos = postService.findByUserId(userResponseDto.getId(),
            pageable);

        assertThat(postResponseDtos.getTotalElements(), is(2L));
    }

    @Test
    void 게시글_update_테스트() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserResponseDto userResponseDto = userService.save(userRequestDto);

        PostRequestDto postRequestDto = PostRequestDto.builder()
            .title("Hi")
            .content("Nice to meet you.")
            .userResponseDto(userResponseDto)
            .build();

        PostResponseDto postResponseDto = postService.save(postRequestDto);

        PostRequestDto PostUpdateRequestDto = PostRequestDto.builder()
            .title("Bye")
            .content("See you again.")
            .userResponseDto(userResponseDto)
            .build();

        PostResponseDto updatedPostDto = postService.update(postResponseDto.getId(),
            PostUpdateRequestDto);

        log.info("updated title : {}, content : {}", updatedPostDto.getTitle(),
            updatedPostDto.getContent());
        assertThat(updatedPostDto.getTitle(), is(not(postResponseDto.getTitle())));
        assertThat(updatedPostDto.getContent(), is(not(postResponseDto.getContent())));
    }

    @Test
    void 게시글_deleteById_테스트() {
        assertThrows(EntityNotFoundException.class, () -> {
            UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("Ilhwan Lee")
                .age(27)
                .hobby("Game")
                .build();

            UserResponseDto userResponseDto = userService.save(userRequestDto);

            PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("Hi")
                .content("Nice to meet you.")
                .userResponseDto(userResponseDto)
                .build();

            PostResponseDto postResponseDto = postService.save(postRequestDto);

            postService.deleteById(postResponseDto.getId());

            // expected = EntityNotFoundException.class
            postService.findById(postResponseDto.getId());
        });
    }
}