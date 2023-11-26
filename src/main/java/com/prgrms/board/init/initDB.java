package com.prgrms.board.init;

import com.prgrms.board.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class initDB {

    private final UserService userService;

    @PostConstruct
    public void init(){
        userService.dbinit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class UserService {

        private final EntityManager em;

        public void dbinit() {
            User user1 = new User("최인준", 20, "헬스");
            User user2 = new User("남은찬", 21, "클라이밍");

            em.persist(user1);
            em.persist(user2);
        }

    }

}
