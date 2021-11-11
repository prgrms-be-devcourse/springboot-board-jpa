package kdt.prgms.springbootboard.repository;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

import kdt.prgms.springbootboard.global.config.JpaAuditingConfiguration;
import kdt.prgms.springbootboard.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan.Filter;

@Slf4j
@DataJpaTest(includeFilters = @Filter(
    type = ASSIGNABLE_TYPE,
    classes = JpaAuditingConfiguration.class
))
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 유저가_없으면_빈_결과값이_정상() {
        //when
        var allUsers = userRepository.findAll();

        //then
        assertThat(allUsers, empty());
    }

    @Test
    void 유저_생성_성공() {
        //given
        var userName = "test";
        var userAge = 20;

        //when
        var user = userRepository.save(new User(userName, userAge));

        //then
        log.info("created user: {}", user);
        assertThat(user, hasProperty("id", notNullValue()));
        assertThat(user, hasProperty("name", is(userName)));
        assertThat(user, hasProperty("age", is(userAge)));
        assertThat(user, hasProperty("hobby", nullValue()));
        assertThat(user, hasProperty("createdBy", notNullValue()));
        assertThat(user, hasProperty("createdDate", notNullValue()));
        assertThat(user, hasProperty("lastModifiedBy", notNullValue()));
        assertThat(user, hasProperty("lastModifiedDate", notNullValue()));
    }

    @Test
    void 전체_유저_조회_성공() {
        //given
        var user1 = new User("tester#1", 1);
        entityManager.persist(user1);

        var user2 = new User("tester#2", 2);
        entityManager.persist(user2);

        var user3 = new User("tester#3", 3);
        entityManager.persist(user3);

        //when
        var allUsers = userRepository.findAll();

        //then
        assertThat(allUsers, hasSize(3));
        assertThat(
            allUsers,
            containsInAnyOrder(
                samePropertyValuesAs(user1),
                samePropertyValuesAs(user2),
                samePropertyValuesAs(user3)
            )
        );
    }

    @Test
    void 유저_아이디로_조회_성공() {
        //given
        var user = new User("tester#1", 1);
        entityManager.persist(user);

        //when
        var foundUser = userRepository.findById(user.getId()).get();

        //then
        assertThat(foundUser, samePropertyValuesAs(user));
    }

    @Test
    void 유저_이름으로_조회_성공() {
        //given
        var user1 = new User("tester#1", 1);
        entityManager.persist(user1);

        var user2 = new User("tester#2", 2);
        entityManager.persist(user2);

        //when
        var foundUser = userRepository.findByName(user2.getName()).get();

        //then
        assertThat(foundUser, samePropertyValuesAs(user2));
    }

    @Test
    void 유저_정보_변경_성공() {
        //given
        var user = new User("tester#1", 1);
        entityManager.persist(user);

        //when
        user.changeUserProfile(
            "changed" + user.getName(),
            user.getAge() + 1,
            new Hobby("soccer", HobbyType.SPORTS)
        );
        var foundUser = userRepository.findById(user.getId()).get();

        //then
        assertThat(foundUser, samePropertyValuesAs(user));

    }

    @Test
    void 아이디로_유저_삭제_성공() {
        //given
        var user1 = new User("tester#1", 1);
        entityManager.persist(user1);

        var user2 = new User("tester#2", 2);
        entityManager.persist(user2);

        //when
        userRepository.deleteById(user2.getId());
        var allUsers = userRepository.findAll();

        //then
        assertThat(allUsers, hasSize(1));
        assertThat(
            allUsers,
            containsInAnyOrder(
                samePropertyValuesAs(user1)
            )
        );
    }
}
