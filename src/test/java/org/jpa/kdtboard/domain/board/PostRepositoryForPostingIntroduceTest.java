package org.jpa.kdtboard.domain.board;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Created by yunyun on 2021/10/12.
 */

@Slf4j
@SpringBootTest
public class PostRepositoryForPostingIntroduceTest {

    final String defaultUserName = "홍길동";
    final String defaultTitle = "안녕하세요! 길동이 소개 드리옵니당~";

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;


    @BeforeEach
    void setUp(){
        // user Entity
        User user = new User();
        user.setAge(10);
        user.setHobby("coding");
        user.setName(defaultUserName);
        user.setCreatedBy("Teacher Kim");
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        // post Entity
        IntroducePost introducePost = new IntroducePost();
        introducePost.setTitle(defaultTitle);
        introducePost.setContent("과제~~~~~~~~~~~~~~");
        introducePost.setCreatedBy(defaultUserName);
        introducePost.setCreatedAt(LocalDateTime.now());
        introducePost.setPassword("1234");
        introducePost.setUser(user);
        postRepository.save(introducePost);
    }

    @AfterEach
    void tearDown(){
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("INTRODUCE 게시물의 모든 데이터를 추출할 수 있다.")
    void findAllTest(){
        //Given

        //When
        List<IntroducePost> dataAllSelected = postRepository.findAll();


        //Then
        assertThat(dataAllSelected.size(), is(1));
    }

    @Test
    @DisplayName("INTRODUCE 게시물을 ID를 기준으로 추출할 수 있다.")
    void findByIdTest() throws Throwable {
        //Given
        List<IntroducePost> dataAllSelected = postRepository.findAll();
        IntroducePost criteriaData = dataAllSelected.get(0);
        Long criteriaPostId = criteriaData.getId();

        //When
        IntroducePost selectDataById = (IntroducePost) postRepository.findById(criteriaPostId).orElseThrow( () -> new RuntimeException("id 값에 해당되는 데이터를 찾지 못했습니다."));

        //Then
        assertThat(selectDataById.getCreatedBy(), is(defaultUserName));
        assertThat(selectDataById.getId(), is(criteriaPostId));

    }

    @Test
    @DisplayName("INTRODUCE 게시물을 저장할 수 있다.")
    void saveTest(){
        //Given
        User userData = userRepository.findByName(defaultUserName).get(0);
        LocalDateTime criteriaCreateAt = LocalDateTime.now();

        IntroducePost introducePost = new IntroducePost();
        introducePost.setTitle("4주차 과제를 제출합니다. ");
        introducePost.setContent("과제~~~~~~~~~~~~~~빠샤빠샤!");
        introducePost.setCreatedBy(userData.getName());
        introducePost.setCreatedAt(criteriaCreateAt);
        introducePost.setPassword("1234");
        introducePost.setUser(userData);

        //When
        IntroducePost dataSaved = (IntroducePost) postRepository.save(introducePost);

        //Then
        assertThat(dataSaved.getCreatedBy(), is(userData.getName()));
        assertThat(dataSaved.getCreatedAt(), is(criteriaCreateAt));

    }

    @Test
    @DisplayName("INTRODUCE 게시물을 수정할 수 있다. ")
    @Transactional
    void updateTest() throws Throwable {
        //Given
        List<IntroducePost> dataAllSelected = postRepository.findAll();
        IntroducePost criteriaData = dataAllSelected.get(0);
        Long criteriaPostId = criteriaData.getId();
        assertThat(criteriaData.getTitle(), is(defaultTitle));

        //When
        criteriaData.setTitle("안녕하세요.");

        //Then
        IntroducePost selectedDataById = (IntroducePost) postRepository.findById(criteriaPostId).orElseThrow( () -> new RuntimeException("id 값에 해당되는 데이터를 찾지 못했습니다."));
        assertThat(selectedDataById.getTitle(), not(defaultTitle));
        assertThat(selectedDataById.getTitle(), is("안녕하세요."));
    }

}
