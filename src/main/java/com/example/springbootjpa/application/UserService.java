package com.example.springbootjpa.application;


import com.example.springbootjpa.domain.user.Hobby;
import com.example.springbootjpa.domain.user.User;
import com.example.springbootjpa.domain.user.UserRepository;
import com.example.springbootjpa.ui.dto.user.UserFindResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public long createUser(String name, int age, Hobby hobby) {
        User user = new User(name, age, hobby);
        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    public List<UserFindResponse> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserFindResponse::from)
                .toList();
    }

    public UserFindResponse find(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Invalid user id!! please check user id again")
        );

        return UserFindResponse.from(user);
    }
}
