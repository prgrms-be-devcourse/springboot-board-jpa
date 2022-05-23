package com.kdt.boardMission.repository;

import com.kdt.boardMission.domain.Post;
import com.kdt.boardMission.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setup(){
        postRepository.deleteAll();
        userRepository.deleteAll();
        user = userRepository.save(new User("default name", 20, "hobby"));
    }

    User user;

    @Test
    @DisplayName("포스트 생성")
    public void createPostTest() throws Exception {

        //given
        Post post = new Post(user, "title", "content");
        Post save = postRepository.save(post);

        //when
        Optional<Post> byId = postRepository.findById(post.getId());

        //then
        assertThat(byId).isPresent();
        assertThat(byId.get().getUser()).isEqualTo(this.user);
    }

    @Test
    @DisplayName("포스트 삭제")
    public void deletePostTest() throws Exception {

        //given
        Post post = new Post(user, "title", "content");
        Post save = postRepository.save(post);

        //when
        postRepository.delete(save);

        //then
        Optional<Post> byId = postRepository.findById(post.getId());
        assertThat(byId).isEmpty();
    }

    @Test
    @DisplayName("포스트 수정")
    public void updatePostTest() throws Exception {

        //given
        Post post = new Post(user, "title", "content");
        Post save = postRepository.save(post);

        //when
        save.updateTitle("newTitle");
        postRepository.save(save);

        //then
        Optional<Post> byId = postRepository.findById(post.getId());
        assertThat(byId).isPresent();
        assertThat(byId.get().getTitle()).isEqualTo("newTitle");
    }

}