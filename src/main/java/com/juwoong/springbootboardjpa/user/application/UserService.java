package com.juwoong.springbootboardjpa.user.application;

import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.juwoong.springbootboardjpa.user.application.model.UserDto;
import com.juwoong.springbootboardjpa.user.domain.User;
import com.juwoong.springbootboardjpa.user.domain.constant.Hobby;
import com.juwoong.springbootboardjpa.user.domain.repository.UserRepository;

@Service
@Transactional
public class UserService implements UserProvider {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(String userName, int age, Hobby hobby) {
        User user = new User(userName, age, hobby);
        user = userRepository.save(user);

        return toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User provideUser(Long id) {
        return findById(id);
    }

    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        User user = findById(id);

        return toDto(user);
    }

    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다"));
    }

    private UserDto toDto(User user) {
        return new UserDto(user);
    }

}
