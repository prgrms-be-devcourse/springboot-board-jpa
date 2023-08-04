package com.juwoong.springbootboardjpa.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.juwoong.springbootboardjpa.user.application.model.UserDto;
import com.juwoong.springbootboardjpa.user.domain.User;
import com.juwoong.springbootboardjpa.user.domain.constant.Hobby;
import com.juwoong.springbootboardjpa.user.domain.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(String userName, int age, Hobby hobby) {
        User user = new User(userName, age, hobby);

        User craetedUser = userRepository.save(user);

        return toDto(craetedUser);
    }

    public User searchByIdForPost(Long id) {
        return userRepository.findById(id).get();
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).get();

        return toDto(user);
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getAge(), user.getHobby()
            , user.getPosts(), user.getCreatedAt(), user.getUpdatedAt());
    }

}
