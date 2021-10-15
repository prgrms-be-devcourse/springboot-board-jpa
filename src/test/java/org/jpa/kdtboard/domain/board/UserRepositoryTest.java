package org.jpa.kdtboard.domain.board;

import lombok.extern.slf4j.Slf4j;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.DuplicateFormatFlagsException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.number.OrderingComparison.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by yunyun on 2021/10/11.
 */

@Slf4j
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp(){
        // User Entity
        User user = new User();
        user.setAge(10);
        user.setHobby("coding");
        user.setName("홍길동");
        user.setCreatedBy("Teacher Kim");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("저장된 모든 데이터를 추출할 수 있다.")
    void findAllTest(){
        //Given


        //When
        var userList = userRepository.findAll();

        //Then
        assertThat(userList.size(), is(1));


    }

    @Test
    @DisplayName("ID를 기준으로 데이터를 추출할 수 있다.")
    void findByIdTest(){
        //Given
        var userSelected = userRepository.findAll().get(0);
        var id = userSelected.getId();
        var name = userSelected.getName();

        //When
        var dataSelectedById = userRepository.findAllById(Collections.singleton(id));
        var dataSelectedByIdNonexstent = userRepository.findAllById(Collections.singleton(id+100));

        //Then
        assertThat(dataSelectedById.size(), is(1));
        assertThat(dataSelectedById.get(0).getName(), is(name));
        assertThat(dataSelectedByIdNonexstent.size(), is(0));
    }

    @Test
    @DisplayName("데이터를 저장할 수 있다.")
    void saveTest(){
        //Given
        int age = 10;
        String hobby = "coding";
        String name = "홍동길";
        String createdBy = "Teacher Hong";
        LocalDateTime createdAt = LocalDateTime.now();

        User user = new User();
        user.setAge(age);
        user.setHobby(hobby);
        user.setName(name);
        user.setCreatedBy(createdBy);
        user.setCreatedAt(createdAt);


        //When
        var dataSaved = userRepository.save(user);

        //Then
        assertThat(dataSaved.getAge(), is(age));
        assertThat(dataSaved.getCreatedBy(), is(createdBy));
        assertThat(dataSaved.getCreatedAt(), is(createdAt));

    }

    @Test
    @DisplayName("데이터를 수정할 수 있다. ")
    @Transactional
    void updateTest(){
        //Given
        var userSelectedBeforeUpdate = userRepository.findAll().get(0);
        assertThat(userSelectedBeforeUpdate.getName(), is("홍길동"));

        //When
        userSelectedBeforeUpdate.setName("김길동");

        //Then
        var userSelectedAfterUpdate = userRepository.findAll().get(0);

        assertThat(userSelectedAfterUpdate.getName(), is("김길동"));

    }

    @Test
    @DisplayName("어떠한 값이 저장하여도, 이름은 유니크한 값이다. ")
    void duplicateCheckForNameWhenInsert(){
        //Given
        User user = new User();
        user.setAge(20);
        user.setHobby("coding");
        user.setName("홍길동"); // setup 에서 저장한 이름과 동일함.
        user.setCreatedBy("Teacher Hong");
        user.setCreatedAt(LocalDateTime.now());


        //When, Then
        assertThrows( DataIntegrityViolationException.class, () -> userRepository.save(user) );

    }


    @Test
    @Transactional
    @DisplayName("어떠한 값이 수정되어도, 이름은 유니크한 값이다.")
    @Disabled
    void duplicateCheckForNameWhenUpdate(){
        //Given
        User user = new User();
        user.setAge(20);
        user.setHobby("coding");
        user.setName("길길동"); // setup 에서 저장한 이름과 동일함.
        user.setCreatedBy("Teacher Hong");
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        var userSelected = userRepository.findByName("길길동").get(0);

        //When, Then
        assertThrows( DataIntegrityViolationException.class, () -> {
            userSelected.setName("홍길동");
            //userRepository.findAll();
        } );

    }



}