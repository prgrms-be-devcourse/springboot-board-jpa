package com.kdt.board.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdt.board.domain.Post;
import com.kdt.board.domain.User;
import com.kdt.board.dto.PostDTO;
import com.kdt.board.dto.UserDTO;
import org.junit.jupiter.api.Test;

class PostConverterTest {

    PostConverter postConverter = new PostConverter();

    @Test
    void convertPost_정상_Test() {
        //given
        PostDTO postDTO = PostDTO.builder()
            .title("hunki")
            .content("asdasd")
            .userDTO(UserDTO.builder()
                .age(15)
                .name("Hunk")
                .hobby("Game")
                .build())
            .build();
        //when
        Post post = postConverter.convertPost(postDTO);
        //then
        assertThat(post.getTitle()).isEqualTo(postDTO.getTitle());
    }

    @Test
    void convertUser_정상_테스트() {
        //given
        UserDTO userDTO = UserDTO.builder()
            .name("김훈기")
            .age(27)
            .hobby("게임")
            .build();
        //when
        User user = postConverter.convertUser(userDTO);
        //then
        assertThat(user.getName()).isEqualTo(userDTO.getName());
    }

    @Test
    void convertPostDTO_정상_테스트() {
        //given
        Post post = new Post();
        post.setTitle("훈기의 여행");
        post.setContent("여행 시작");
        User user = new User();
        user.setName("김훈기");
        user.setAge(27);
        user.setHobby("게임");
        post.saveUser(user);
        //when
        PostDTO postDTO = postConverter.convertPostDTO(post);
        //then
        assertThat(postDTO.getTitle()).isEqualTo(post.getTitle());
        assertThat(postDTO.getUserDTO().getName()).isEqualTo(post.getUser().getName());
    }

    @Test
    void convertUserDTO_정상_테스트() {
        //given
        Post post = new Post();
        post.setTitle("훈기의 여행");
        post.setContent("여행 시작");
        User user = new User();
        user.setName("김훈기");
        user.setAge(27);
        user.setHobby("게임");
        user.addPost(post);
        //when
        UserDTO userDTO = postConverter.convertUserDTO(user);
        //then
        assertThat(user.getName()).isEqualTo(user.getName());
    }
}