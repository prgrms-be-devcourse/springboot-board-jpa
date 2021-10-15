package org.jpa.kdtboard.domain.board;



import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Created by yunyun on 2021/10/12.
 */

@Slf4j
@SpringBootTest
public class PostRepositoryForPostingQuestionTest {

    final String defaultUserName = "홍길동";
    final String defaultTitle = "Java p304 에서 궁금한 점이 있습니다. HELP ME!!";
    final PostScope defaultPostScope = PostScope.PUBLIC;

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
        QuestionPost questionPost = new QuestionPost();
        questionPost.setTitle(defaultTitle);
        questionPost.setContent("과제~~~~~~~~~~~~~~");
        questionPost.setCreatedBy(defaultUserName);
        questionPost.setCreatedAt(LocalDateTime.now());
        questionPost.setUser(user);
        questionPost.setPassword("1234");
        questionPost.setPostScope(defaultPostScope);
        postRepository.save(questionPost);
    }

    @AfterEach
    void tearDown(){
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("QUESTION 게시물의 모든 데이터를 추출할 수 있다.")
    void findAllTest(){
        //Given

        //When
        List<QuestionPost> dataAllSelected = postRepository.findAll();

        //Then
        assertThat(dataAllSelected.size(), is(1));
    }

    @Test
    @DisplayName("QUESTION 게시물을 ID를 기준으로 추출할 수 있다.")
    void findByIdTest() throws Throwable {
        //Given
        List<QuestionPost> dataAllSelected = postRepository.findAll();
        QuestionPost criteriaData = dataAllSelected.get(0);
        Long criteriaPostId = criteriaData.getId();

        //When
        QuestionPost selectDataById = (QuestionPost) postRepository.findById(criteriaPostId).orElseThrow( () -> new RuntimeException("id 값에 해당되는 데이터를 찾지 못했습니다."));

        //Then
        assertThat(selectDataById.getCreatedBy(), is(defaultUserName));
        assertThat(selectDataById.getId(), is(criteriaPostId));
    }

    @Test
    @DisplayName("QUESTION 게시물을 저장할 수 있다.")
    void saveTest(){
        //Given
        User userData = userRepository.findByName(defaultUserName).get(0);
        LocalDateTime criteriaCreateAt = LocalDateTime.now();
        PostScope criteriaPostScope = PostScope.PRIVATE;

        QuestionPost questionPost = new QuestionPost();
        questionPost.setTitle("4주차 과제를 제출합니다. ");
        questionPost.setContent("과제~~~~~~~~~~~~~~빠샤빠샤!");
        questionPost.setCreatedBy(userData.getName());
        questionPost.setCreatedAt(criteriaCreateAt);
        questionPost.setPassword("1234");
        questionPost.setPostScope(criteriaPostScope);
        questionPost.setUser(userData);

        //When
        QuestionPost dataSaved = (QuestionPost) postRepository.save(questionPost);

        //Then
        assertThat(dataSaved.getCreatedBy(), is(userData.getName()));
        assertThat(dataSaved.getCreatedAt(), is(criteriaCreateAt));
        assertThat(dataSaved.getPostScope(), is(criteriaPostScope));
    }

    @Test
    @DisplayName("QUESTION 게시물을 수정할 수 있다. ")
    @Transactional
    void updateTest() throws Throwable {
        //Given
        List<QuestionPost> dataAllSelected = postRepository.findAll();
        QuestionPost criteriaData = dataAllSelected.get(0);
        Long criteriaPostId = criteriaData.getId();
        assertThat(criteriaData.getTitle(), is(defaultTitle));

        //When
        criteriaData.setTitle("Java p314 에서 궁금한 점이 있습니다. HELP ME!!");

        //Then
        QuestionPost selectedDataById = (QuestionPost) postRepository.findById(criteriaPostId).orElseThrow( () -> new RuntimeException("id 값에 해당되는 데이터를 찾지 못했습니다."));
        assertThat(selectedDataById.getTitle(), not(containsString("p304")));
        assertThat(selectedDataById.getTitle(), containsString("p314"));

    }


}
