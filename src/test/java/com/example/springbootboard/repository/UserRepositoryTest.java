package com.example.springbootboard.repository;

import com.example.springbootboard.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.springbootboard.entity.Hobby.getRandomHobby;
import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
// @DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest{// extends MySQLContainer {

    @Autowired
    private UserRepository repository;

    @Autowired
    EntityManagerFactory emf;

    List<User> getUsers(int n){
        List<User> users = new ArrayList<>();
        for(int i = 0; i < n; i++){
            users.add(User.builder().name("user" + i).age(20 + i).hobby(getRandomHobby()).build());
        }
        return users;
    }


    @AfterEach
    void cleanUp(){
        repository.deleteAll();
        //entityManagr
//        emf.createEntityManager()
//            .createNativeQuery("ALTER TABLE users AUTO_INCREMENT = 1;")
//            .executeUpdate();
    }


    @Test
    @DisplayName("저장, 조회")
    void persistenceTest () {
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
}
