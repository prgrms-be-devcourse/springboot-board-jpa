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
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by yunyun on 2021/10/12.
 */
@Slf4j
@SpringBootTest
public class PostRepositoryForPostingHomeworkTest {

    final String defaultUserName = "홍길동";
    final String defaultTitle = "과제를 제출합니다.";
    final HomeworkStatus defaultHomeworkStatus = HomeworkStatus.ONGOING;

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
        HomeworkPost homeworkPost = new HomeworkPost();
        homeworkPost.setTitle(defaultTitle);
        homeworkPost.setContent("과제~~~~~~~~~~~~~~");
        homeworkPost.setCreatedBy(defaultUserName);
        homeworkPost.setCreatedAt(LocalDateTime.now());
        homeworkPost.setPassword("1234");
        homeworkPost.setUser(user);
        homeworkPost.setHomeworkStatus(defaultHomeworkStatus);
        postRepository.save(homeworkPost);
    }

    @AfterEach
    void tearDown(){
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("HOMEWORK 게시물의 모든 데이터를 추출할 수 있다.")
    void findAllTest(){
        //Given

        //When
        List<HomeworkPost> dataAllSelected = postRepository.findAll();
        HomeworkPost dataOneSelected = dataAllSelected.get(0);

        //Then
        assertThat(dataAllSelected.size(), is(1));
        assertThat(dataOneSelected.getTitle(), is(defaultTitle));
        assertThat(dataOneSelected.getHomeworkStatus(), is(defaultHomeworkStatus));
        assertThat(dataOneSelected.getUser().getName(), is(defaultUserName));
    }

    @Test
    @DisplayName("HOMEWORK 게시물을 ID를 기준으로 추출할 수 있다.")
    void findByIdTest() throws Throwable {
        //Given
        List<HomeworkPost> dataAllSelected = postRepository.findAll();
        HomeworkPost criteriaData = dataAllSelected.get(0);
        Long criteriaPostId = criteriaData.getId();

        //When
        HomeworkPost selectDataById = (HomeworkPost) postRepository.findById(criteriaPostId).orElseThrow( () -> new RuntimeException("id 값에 해당되는 데이터를 찾지 못했습니다."));

        //Then
        assertThat(selectDataById.getHomeworkStatus(), is(criteriaData.getHomeworkStatus()));
        assertThat(selectDataById.getId(), is(criteriaPostId));
    }

    @Test
    @DisplayName("HOMEWORK 게시물을 저장할 수 있다.")
    void saveTest(){
        //Given
        User userData = userRepository.findByName(defaultUserName).get(0);
        LocalDateTime criteriaCreateAt = LocalDateTime.now();

        HomeworkPost homeworkPost = new HomeworkPost();
        homeworkPost.setTitle("4주차 과제를 제출합니다. ");
        homeworkPost.setContent("과제~~~~~~~~~~~~~~빠샤빠샤!");
        homeworkPost.setCreatedBy(userData.getName());
        homeworkPost.setCreatedAt(criteriaCreateAt);
        homeworkPost.setPassword("1234");
        homeworkPost.setUser(userData);
        homeworkPost.setHomeworkStatus(defaultHomeworkStatus);

        //When
        HomeworkPost dataSaved = (HomeworkPost) postRepository.save(homeworkPost);

        //Then
        assertThat(dataSaved.getCreatedBy(), is(userData.getName()));
        assertThat(dataSaved.getCreatedAt(), is(criteriaCreateAt));
    }

    @Test
    @DisplayName("HOMEWORK 게시물을 수정할 수 있다. ")
    @Transactional
    void updateTest() throws Throwable {
        //Given
        List<HomeworkPost> dataAllSelected = postRepository.findAll();
        HomeworkPost criteriaData = dataAllSelected.get(0);
        Long criteriaPostId = criteriaData.getId();

        //When
        criteriaData.setHomeworkStatus(HomeworkStatus.COMPLETED);
        criteriaData.setTitle("과제 제출 완료, 확인바랍니다.");

        //Then
        HomeworkPost selectedDataById = (HomeworkPost) postRepository.findById(criteriaPostId).orElseThrow( () -> new RuntimeException("id 값에 해당되는 데이터를 찾지 못했습니다."));
        assertThat(selectedDataById.getHomeworkStatus(), is(HomeworkStatus.COMPLETED));
        assertThat(selectedDataById.getTitle(), is("과제 제출 완료, 확인바랍니다."));
    }



}
