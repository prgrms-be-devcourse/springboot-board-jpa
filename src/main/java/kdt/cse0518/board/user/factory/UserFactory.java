package kdt.cse0518.board.user.factory;

import kdt.cse0518.board.user.entity.User;
import kdt.cse0518.board.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserFactory {

    private final UserRepository repository;

    public UserFactory(final UserRepository repository) {
        this.repository = repository;
    }

    public User createUser(final String name, final int age, final String hobby) {
        return User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }
}
