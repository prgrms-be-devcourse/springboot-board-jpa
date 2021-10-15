package org.jpa.kdtboard.post.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jpa.kdtboard.domain.board.*;
import org.jpa.kdtboard.post.converter.PostConverter;
import org.jpa.kdtboard.post.dto.PostDto;
import org.jpa.kdtboard.post.dto.PostType;
import org.jpa.kdtboard.common.Encrypt;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 * Created by yunyun on 2021/10/13.
 */

@Slf4j
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService postService;

    @Autowired
    PostConverter postConverter;

    // User 데이터는 정보가 바뀌지 않음으로, 따로 빼놓았습니다.
    private final String defaultUserName = "홍길동";
    private User defaultUserData = null;

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

        var userDataAllByName = userRepository.findByName("홍길동");
        if (userDataAllByName.isEmpty()) throw new RuntimeException("유효한 사용자가 아닙니다.");
        defaultUserData = userDataAllByName.get(0);
    }

    @AfterEach
    void tearDown(){
        postRepository.deleteAll();
        userRepository.deleteAll();

    }

    HomeworkPost saveDataAndThenReturnSavedDataForHomeworkPosting(User userdata) throws Throwable {
        HomeworkPost homeworkPost = new HomeworkPost();
        homeworkPost.setContent("정확히 이야기하면 Entity는 반드시 파라미터가 없는 생성자가 있어야 하고...");
        homeworkPost.setTitle("JPA 과제 제출합니다.");
        homeworkPost.setCreatedBy(defaultUserName);
        homeworkPost.setPassword(Encrypt.SHA256("1234"));
        homeworkPost.setHomeworkStatus(HomeworkStatus.COMPLETED);
        homeworkPost.setUser(userdata);
        return (HomeworkPost) postRepository.save(homeworkPost);
    }
    IntroducePost saveDataAndThenReturnSavedDataForIntroducePosting(User userdata) throws Throwable {
        IntroducePost introducePost = new IntroducePost();
        introducePost.setContent("사실 저는 개명하였습니다. 그래서 본명은...");
        introducePost.setTitle("잘 부탁드립니다. 홍길동입니다.");
        introducePost.setCreatedBy(defaultUserName);
        introducePost.setPassword(Encrypt.SHA256("1234"));
        introducePost.setUser(userdata);
        return (IntroducePost) postRepository.save(introducePost);
    }
    NoticePost saveDataAndThenReturnSavedDataForNoticePosting(User userdata) throws Throwable {
        NoticePost noticePost = new NoticePost();
        noticePost.setContent("장소와 시간은 아래와 같습니다. 회비는...");
        noticePost.setTitle("오늘 번개모임이 있습니다. 확인바랍니다.");
        noticePost.setCreatedBy(defaultUserName);
        noticePost.setPassword(Encrypt.SHA256("1234"));
        noticePost.setExpireDate(LocalDateTime.of(2021, 12,25, 12, 00, 00));
        noticePost.setUser(userdata);
        return (NoticePost) postRepository.save(noticePost);
    }
    QuestionPost saveDataAndThenReturnSavedDataForQuestionPosting(User userdata) throws Throwable {
        QuestionPost questionPost = new QuestionPost();
        questionPost.setContent("왜 Entity는 기본 생성자가 필요한지 궁금합니다. 그리고 더욱이..");
        questionPost.setTitle("Entity 관련 질문이 있습니다.");
        questionPost.setCreatedBy(defaultUserName);
        questionPost.setPassword(Encrypt.SHA256("1234"));
        questionPost.setPostScope(PostScope.PRIVATE);
        questionPost.setUser(userdata);
        return (QuestionPost) postRepository.save(questionPost);
    }

    @Test
    @DisplayName("모든 데이터를 볼 수 있다.")
    void findAllTest() throws Throwable {
        //Given
        saveDataAndThenReturnSavedDataForHomeworkPosting(defaultUserData);
        saveDataAndThenReturnSavedDataForIntroducePosting(defaultUserData);
        saveDataAndThenReturnSavedDataForNoticePosting(defaultUserData);
        saveDataAndThenReturnSavedDataForQuestionPosting(defaultUserData);

        //When
        List<PostDto> dataAll = postService.findAll();

        //When
        assertThat(dataAll.size(), is(4));
    }

    @Test
    @DisplayName("해당 Id를 기준으로 과제 제출 게시물을 추출할 수 있다.")
    void findByIdTestForHomeworkPostingData() throws Throwable {
        //Given
        var homeworkPostDto = postConverter.convertDto(saveDataAndThenReturnSavedDataForHomeworkPosting(defaultUserData));

        //When
        var dataFindById = postService.findById(homeworkPostDto.getId());

        //Then
        assertThat(dataFindById.getTitle(), is(homeworkPostDto.getTitle()));
        assertThat(dataFindById.getCreatedBy(), is(homeworkPostDto.getCreatedBy()));
        assertThat(dataFindById.getCreatedAt(), is(homeworkPostDto.getCreatedAt()));
        assertThat(dataFindById.getHomeworkStatus(), is(homeworkPostDto.getHomeworkStatus()));
        assertThat(dataFindById.getPassword(), emptyString());
    }

    @Test
    @DisplayName("해당 Id를 기준으로 자기소개 게시물을 추출할 수 있다.")
    void findByIdTestForIntroducePostingData() throws Throwable {
        //Given
        var introducePostDto = postConverter.convertDto(saveDataAndThenReturnSavedDataForIntroducePosting(defaultUserData));

        //When
        var dataFindById = postService.findById(introducePostDto.getId());

        //Then
        assertThat(dataFindById.getTitle(), is(introducePostDto.getTitle()));
        assertThat(dataFindById.getCreatedBy(), is(introducePostDto.getCreatedBy()));
        assertThat(dataFindById.getCreatedAt(), is(introducePostDto.getCreatedAt()));
        assertThat(dataFindById.getPassword(), emptyString());
    }
    @Test
    @DisplayName("해당 Id를 기준으로 공지 게시물을 추출할 수 있다.")
    void findByIdTestForNoticePostingData() throws Throwable {
        //Given
        var noticePostDto = postConverter.convertDto(saveDataAndThenReturnSavedDataForNoticePosting(defaultUserData));

        //When
        var dataFindById =  postService.findById(noticePostDto.getId());

        //Then
        assertThat(dataFindById.getTitle(), is(noticePostDto.getTitle()));
        assertThat(dataFindById.getCreatedBy(), is(noticePostDto.getCreatedBy()));
        assertThat(dataFindById.getCreatedAt(), is(noticePostDto.getCreatedAt()));
        assertThat(dataFindById.getPassword(), emptyString());
        assertThat(dataFindById.getExpireDate(), is(noticePostDto.getExpireDate()));
    }
    @Test
    @DisplayName("해당 Id를 기준으로 질문 게시물을 추출할 수 있다.")
    void findByIdTestForQuestionPostingData() throws Throwable {
        //Given
        var questionPostDto = postConverter.convertDto(saveDataAndThenReturnSavedDataForQuestionPosting(defaultUserData));

        //When
        var dataFindById = postService.findById(questionPostDto.getId());

        //Then
        assertThat(dataFindById.getTitle(), is(questionPostDto.getTitle()));
        assertThat(dataFindById.getCreatedBy(), is(questionPostDto.getCreatedBy()));
        assertThat(dataFindById.getCreatedAt(), is(questionPostDto.getCreatedAt()));
        assertThat(dataFindById.getPassword(), emptyString());
        assertThat(dataFindById.getPostScope(), is(questionPostDto.getPostScope()));
    }

    @Test
    @DisplayName("과제 제출 게시물을 저장할 수 있다.")
    void saveTestForHomeworkPostingData() throws Throwable {
        //Given
        var criteriaTitle = "JPA 과제 제출합니다.";
        var criteriaHomeworkStatus = HomeworkStatus.COMPLETED;


        //When
        var homeworkPostIdSaved = postService.save(PostDto.builder()
                .postType(PostType.HOMEWORK)
                .content("정확히 이야기하면 Entity는 반드시 파라미터가 없는 생성자가 있어야 하고...")
                .title(criteriaTitle)
                .createdBy(defaultUserName)
                .password("1234")
                .homeworkStatus(criteriaHomeworkStatus)
                .build()
        );

        //Then
        var homeworkPostSelectedById = (HomeworkPost) postRepository.findById(homeworkPostIdSaved).orElseThrow(() -> new RuntimeException("id 값에 해당되는 데이터를 찾지 못했습니다."));
        assertThat(homeworkPostSelectedById.getTitle(), is(criteriaTitle));
        assertThat(homeworkPostSelectedById.getHomeworkStatus(), is(criteriaHomeworkStatus));
    }

    @Test
    @DisplayName("자기소개 게시물을 저장할 수 있다.")
    void saveTestForIntroducePostingData() throws Throwable {
        //Given
        var criteriaTitle = "잘 부탁드립니다. 홍길동 입니다.";
        var criteriaPassword = "1234";

        //When
        var introducePostIdSaved = postService.save(PostDto.builder()
                .postType(PostType.INTRODUCE)
                .content("사실 저는 개명하였습니다. 그래서 본명은...")
                .title(criteriaTitle)
                .createdBy(defaultUserName)
                .password(criteriaPassword)
                .build());

        //Then
        var introducePostSelectedById = (IntroducePost)postRepository
                .findById(introducePostIdSaved)
                .orElseThrow(() -> new NotFoundException("id 값에 해당되는 데이터를 찾지 못했습니다."));

        assertThat(introducePostSelectedById.getTitle(), is(criteriaTitle));
        assertThat(introducePostSelectedById.getPassword(), is(Encrypt.SHA256(criteriaPassword)));


    }

    @Test
    @DisplayName("공지 게시물을 저장할 수 있다.")
    void saveTestForNoticePostingData() throws Throwable {
        //Given
        var criteriaTitle = "오늘 번개모임이 있습니다. 확인바랍니다.";
        var criteriaExpireDate = LocalDateTime.of(2021, 12,25, 12, 00, 00);


        //When
        var noticePostIdSaved =postService.save(PostDto.builder()
                .postType(PostType.NOTICE)
                .content("장소와 시간은 아래와 같습니다. 회비는...")
                .title(criteriaTitle)
                .createdBy(defaultUserName)
                .password("1234")
                .expireDate(criteriaExpireDate)
                .build());

        //Then
        var noticePostSelectedById = (NoticePost)postRepository
                .findById(noticePostIdSaved)
                .orElseThrow(() -> new NotFoundException("id 값에 해당되는 데이터를 찾지 못했습니다."));

        assertThat(noticePostSelectedById.getTitle(), is(criteriaTitle));
        assertThat(noticePostSelectedById.getExpireDate(), is(criteriaExpireDate));

    }

    @Test
    @DisplayName("질문 게시물을 저장할 수 있다.")
    void saveTestForQuestionPostingData() throws Throwable {
        //Given
        var criteriaTitle = "Entity 관련 질문이 있습니다.";
        var criteriaPostScope = PostScope.PRIVATE;

        //When
        var questionPostIdSaved = postService.save(PostDto.builder()
                .postType(PostType.QUESTION)
                .content("왜 Entity는 기본 생성자가 필요한지 궁금합니다. 그리고 더욱이..")
                .title(criteriaTitle)
                .createdBy(defaultUserName)
                .password("1234")
                .postScope(criteriaPostScope)
                .build());

        //Then
        var questionPostSelectedById = (QuestionPost) postRepository
                .findById(questionPostIdSaved)
                .orElseThrow(() -> new NotFoundException("id 값에 해당되는 데이터를 찾지 못했습니다."));

        assertThat(questionPostSelectedById.getTitle(), is(criteriaTitle));
        assertThat(questionPostSelectedById.getPostScope(), is(criteriaPostScope));
    }

    @Test
    @DisplayName("과제 제출 게시물을 수정할 수 있다.")
    @Transactional
    void updateTestForHomeworkPostingData() throws Throwable {
        //Given
        var homeworkPostEntitySaved = saveDataAndThenReturnSavedDataForHomeworkPosting(defaultUserData);
        assertThat(homeworkPostEntitySaved.getHomeworkStatus(), not(HomeworkStatus.REJECTED));
        var homeworkPostDto = postConverter.convertDto(homeworkPostEntitySaved);

        //When
        homeworkPostDto.setHomeworkStatus(HomeworkStatus.REJECTED);
        postService.update(homeworkPostDto.getId(), homeworkPostDto);

        //Then
        var homeworkPostEntityFindByIdAfterUpdate = (HomeworkPost)postRepository
                .findById(homeworkPostDto.getId())
                .orElseThrow(() -> new NotFoundException("id 값에 해당되는 데이터를 찾지 못했습니다."));

        assertThat(homeworkPostEntityFindByIdAfterUpdate.getHomeworkStatus(), is(HomeworkStatus.REJECTED));
    }

    @Test
    @DisplayName("자기 소개 게시물을 수정할 수 있다.")
    @Transactional
    void updateTestForIntroducePostingData() throws Throwable {
        //Given
        var introducePostEntitySaved = saveDataAndThenReturnSavedDataForIntroducePosting(defaultUserData);
        assertThat(introducePostEntitySaved.getTitle(), not("안녕!"));
        var introducePostDto = postConverter.convertDto(introducePostEntitySaved);

        //When
        introducePostDto.setTitle("안녕!");
        postService.update(introducePostDto.getId(), introducePostDto);

        //Then
        var introducePostEntityFindByIdAfterUpdate = (IntroducePost)postRepository
                .findById(introducePostDto.getId())
                .orElseThrow(() -> new NotFoundException("id 값에 해당되는 데이터를 찾지 못했습니다."));

        assertThat(introducePostEntityFindByIdAfterUpdate.getTitle(), is("안녕!"));
    }

    @Test
    @DisplayName("공지 게시물을 수정할 수 있다.")
    @Transactional
    void updateTestForNoticePostingData() throws Throwable {
        //Given
        var noticePostEntitySaved = saveDataAndThenReturnSavedDataForNoticePosting(defaultUserData);
        assertThat(noticePostEntitySaved.getExpireDate(), not(LocalDateTime.of(2022, 01, 01, 12, 00,00)));
        var noticePostDto = postConverter.convertDto(noticePostEntitySaved);

        //When
        noticePostDto.setExpireDate(LocalDateTime.of(2022, 01, 01, 12, 00,00));
        noticePostDto.getId();
        System.out.println(noticePostDto);
        postService.update(noticePostDto.getId(), noticePostDto);

        //Then
        var noticePostEntityFindByIdAfterUpdate = (NoticePost)postRepository
                .findById(noticePostDto.getId())
                .orElseThrow(() -> new NotFoundException("id 값에 해당되는 데이터를 찾지 못했습니다."));

        assertThat(noticePostEntityFindByIdAfterUpdate.getExpireDate(), is(LocalDateTime.of(2022, 01, 01, 12, 00,00)));

    }

    @Test
    @DisplayName("질문 게시물을 수정할 수 있다.")
    @Transactional
    void updateTestForQuestionPostingData() throws Throwable {
        //Given
        var questionPostEntitySaved = saveDataAndThenReturnSavedDataForQuestionPosting(defaultUserData);
        assertThat(questionPostEntitySaved.getPostScope(), not(PostScope.PUBLIC));
        var questionPostDto = postConverter.convertDto(questionPostEntitySaved);

        //When
        questionPostDto.setPostScope(PostScope.PUBLIC);
        postService.update(questionPostDto.getId(), questionPostDto);

        //Then
        var questionPostEntityFindByIdAfterUpdate = (QuestionPost)postRepository
                .findById(questionPostDto.getId())
                .orElseThrow(() -> new NotFoundException("id 값에 해당되는 데이터를 찾지 못했습니다."));

        assertThat(questionPostEntityFindByIdAfterUpdate.getPostScope(), is(PostScope.PUBLIC));
    }


}