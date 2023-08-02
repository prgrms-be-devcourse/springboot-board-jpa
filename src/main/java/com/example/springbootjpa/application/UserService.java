package com.example.springbootjpa.application;


import com.example.springbootjpa.domain.user.Hobby;
import com.example.springbootjpa.domain.user.User;
import com.example.springbootjpa.domain.user.UserRepository;
import com.example.springbootjpa.ui.dto.user.UserFindResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public long createUser(String name, int age, Hobby hobby) {
        User user = new User(name, age, hobby);
        userRepository.save(user);

        return user.getId();
    }

    public List<UserFindResponse> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserFindResponse::from)
                .toList();
    }

    public UserFindResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("member doesn't exist")
        );

        return UserFindResponse.from(user);
    }
}
