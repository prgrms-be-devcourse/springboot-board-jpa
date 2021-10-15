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
public class PostRepositoryForPostingNoticeTest {

    final String defaultUserName = "홍길동";
    final String defaultTitle = "청소 당번에 대한 안내드립니다. 꼭! 확인하세요!";
    final LocalDateTime defaultExpireDate = LocalDateTime.of(2021, 11, 11, 13, 00);
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
        NoticePost noticePost = new NoticePost();
        noticePost.setTitle(defaultTitle);
        noticePost.setContent("과제~~~~~~~~~~~~~~");
        noticePost.setCreatedBy(defaultUserName);
        noticePost.setCreatedAt(LocalDateTime.now());
        noticePost.setUser(user);
        noticePost.setPassword("1234");
        noticePost.setExpireDate(defaultExpireDate);
        postRepository.save(noticePost);
    }

    @AfterEach
    void tearDown(){
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("NOTICE 게시물의 모든 데이터를 추출할 수 있다.")
    void findAllTest(){
        //Given

        //When
        List<NoticePost> dataAllSelected = postRepository.findAll();


        //Then
        assertThat(dataAllSelected.size(), is(1));
    }

    @Test
    @DisplayName("NOTICE 게시물을 ID를 기준으로 추출할 수 있다.")
    void findByIdTest() throws Throwable {
        //Given
        List<NoticePost> dataAllSelected = postRepository.findAll();
        NoticePost criteriaData = dataAllSelected.get(0);
        Long criteriaPostId = criteriaData.getId();

        //When
        NoticePost selectDataById = (NoticePost) postRepository.findById(criteriaPostId).orElseThrow( () -> new RuntimeException("id 값에 해당되는 데이터를 찾지 못했습니다.") );

        //Then
        assertThat(selectDataById.getCreatedBy(), is(defaultUserName));
        assertThat(selectDataById.getId(), is(criteriaPostId));
        assertThat(selectDataById.getExpireDate(), is(defaultExpireDate));
    }

    @Test
    @DisplayName("NOTICE 게시물을 저장할 수 있다.")
    void saveTest(){
        //Given
        User userData = userRepository.findByName(defaultUserName).get(0);
        LocalDateTime criteriaCreateAt = LocalDateTime.now();
        LocalDateTime criteriaExpireDate = LocalDateTime.of(2021, 12, 12, 12, 00);

        NoticePost noticePost = new NoticePost();
        noticePost.setTitle("4주차 과제를 제출합니다. ");
        noticePost.setContent("과제~~~~~~~~~~~~~~빠샤빠샤!");
        noticePost.setCreatedBy(userData.getName());
        noticePost.setCreatedAt(criteriaCreateAt);
        noticePost.setPassword("1234");
        noticePost.setExpireDate(criteriaExpireDate);
        noticePost.setUser(userData);

        //When
        NoticePost dataSaved = (NoticePost) postRepository.save(noticePost);

        //Then
        assertThat(dataSaved.getCreatedBy(), is(userData.getName()));
        assertThat(dataSaved.getCreatedAt(), is(criteriaCreateAt));
        assertThat(dataSaved.getExpireDate(), is(criteriaExpireDate));

    }

    @Test
    @DisplayName("NOTICE 게시물을 수정할 수 있다. ")
    @Transactional
    void updateTest() throws Throwable {
        //Given
        List<NoticePost> dataAllSelected = postRepository.findAll();
        NoticePost criteriaData = dataAllSelected.get(0);
        Long criteriaPostId = criteriaData.getId();
        assertThat(criteriaData.getTitle(), is(defaultTitle));

        //When
        criteriaData.setTitle("공짜 아이템 나눠줍니다. 교실 앞으로 오세요.");

        //Then
        NoticePost selectedDataById = (NoticePost) postRepository.findById(criteriaPostId).orElseThrow( () -> new RuntimeException("id 값에 해당되는 데이터를 찾지 못했습니다."));
        assertThat(selectedDataById.getTitle(), not(defaultTitle));
        assertThat(selectedDataById.getTitle(), is("공짜 아이템 나눠줍니다. 교실 앞으로 오세요."));
    }

}
