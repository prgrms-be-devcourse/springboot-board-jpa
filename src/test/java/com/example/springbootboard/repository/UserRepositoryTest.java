package com.example.springbootboard.repository;

import com.example.springbootboard.entity.Hobby;
import com.example.springbootboard.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.springbootboard.entity.Hobby.getRandomHobby;
import static org.assertj.core.api.Assertions.assertThat;



// @SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {// extends MySQLContainer {

    @Autowired
    private UserRepository repository;

    @Autowired
    private EntityManagerFactory emf;

    List<User> getUsers(int n){
        List<User> users = new ArrayList<>();
        for(int i = 0; i < n; i++){
            users.add(User.builder().name("user" + i).age(20 + i).hobby(getRandomHobby()).build());
        }
        return users;
    }


//    @AfterEach
//    void cleanUp(){
//        repository.deleteAll();
//        //entityManagr
////        emf.createEntityManager()
////            .createNativeQuery("ALTER TABLE users AUTO_INCREMENT = 1;")
////            .executeUpdate();
//    }
//    @BeforeEach
//    void setUp(){
//        repository.deleteAll();
//    }


    @Test
    @DisplayName("저장, 조회")
    void persistenceSaveNFindTest () {
        // Given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        User user = getUsers(1).get(0);

        // When
        transaction.begin();
        em.persist(user);
        transaction.commit();

        // Then
        Optional<User> retrieved = repository.findById(1L);
        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getName()).isEqualTo("user0");
        assertThat(retrieved.get().getAge()).isEqualTo(20);
    }

    @Test
    @DisplayName("수정 테스트")
    void updateTest() {
        // Given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        User user = User.builder().name("tmp").age(22).hobby(Hobby.SOCCER).build();

        // When
        transaction.begin();
        em.persist(user);
        transaction.commit();

        transaction.begin();
        user.changeHobby(Hobby.CODING);
        transaction.commit();

        // Then
        User user0 = em.find(User.class, 1L);
        assertThat(user0.getHobby()).isEqualTo(Hobby.CODING);
    }

    @Test
    @DisplayName("삭제 테스트")
    void deleteTest() {
        // Given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        User user = getUsers(1).get(0);

        // When
        transaction.begin();
        em.persist(user);
        transaction.commit();

        transaction.begin();
        em.remove(user);
        transaction.commit();

        // Then
        List<User> users = repository.findAll();
        assertThat(users).hasSize(0);
    }
}
