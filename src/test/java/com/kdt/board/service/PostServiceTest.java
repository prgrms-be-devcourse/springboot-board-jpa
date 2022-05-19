package com.kdt.board.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdt.board.domain.User;
import com.kdt.board.dto.PostDTO;
import com.kdt.board.dto.UserDTO;
import com.kdt.board.repository.PostRepository;
import com.kdt.board.repository.UserRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    void savePost_정상_테스트() {
        //given
        PostDTO postDTO = PostDTO.builder()
            .title("훈기의 여행")
            .content("여행을 합니다.")
            .userDTO(UserDTO.builder()
                .name("김훈기")
                .age(27)
                .hobby("게임").build()
            )
            .build();
        //when
        Long id = postService.savePost(postDTO);
        Long userId = postService.findPostById(id).getUserDTO().getId();
        PostDTO postDTO2 = PostDTO.builder()
            .title("훈기의 여행2")
            .content("여행을 합니다.2")
            .userDTO(UserDTO.builder()
                .id(userId)
                .build()
            )
            .build();
        PostDTO postDTO3 = PostDTO.builder()
            .title("훈기의 여행3")
            .content("여행을 합니다.3")
            .userDTO(UserDTO.builder()
                .id(userId)
                .build()
            )
            .build();
        Long id2 = postService.savePost(postDTO2);
        Long id3 = postService.savePost(postDTO3);
        //then
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            int size = user.get().getPosts().size();
            assertThat(size).isEqualTo(3);
        } else {
            throw new IllegalArgumentException();
        }
    }


    @Test
    void findPostById_정상_테스트() {
        //given
        PostDTO postDTO = PostDTO.builder()
            .title("훈기의 여행")
            .content("여행을 합니다.")
            .userDTO(UserDTO.builder()
                .name("김훈기")
                .age(27)
                .hobby("게임").build()
            )
            .build();
        Long id = postService.savePost(postDTO);
        //when
        PostDTO responsePostDTO = postService.findPostById(id);
        //then
        assertThat(responsePostDTO.getTitle()).isEqualTo(postDTO.getTitle());
    }

    @Test
    void findPages_정상_테스트() {
        //given
        PostDTO postDTO = PostDTO.builder()
            .title("훈기의 여행")
            .content("여행을 합니다.")
            .userDTO(UserDTO.builder()
                .name("김훈기")
                .age(27)
                .hobby("게임").build()
            )
            .build();
        Long id = postService.savePost(postDTO);
        //when
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "title");
        Page<PostDTO> pages = postService.findPostsByPage(pageable);
        //then
        assertThat(pages.getTotalElements()).isEqualTo(1);
    }

    @Test
    void updatePost_정상_테스트() {
        //given
        PostDTO postDTO = PostDTO.builder()
            .title("훈기의 여행")
            .content("여행을 합니다.")
            .userDTO(UserDTO.builder()
                .name("김훈기")
                .age(27)
                .hobby("게임").build()
            )
            .build();
        Long id = postService.savePost(postDTO);
        PostDTO newPostDTO = PostDTO.builder()
            .id(id)
            .title("훈기의 여행 수정")
            .content("수정 여행을 합니다.")
            .build();
        //when
        PostDTO updatedPostDTO = postService.updatePost(id, newPostDTO);
        //then
        assertThat(updatedPostDTO.getTitle()).isEqualTo(newPostDTO.getTitle());
        assertThat(updatedPostDTO.getContent()).isEqualTo(newPostDTO.getContent());
    }
}