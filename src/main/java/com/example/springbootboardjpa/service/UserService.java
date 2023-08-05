package com.example.springbootboardjpa.service;

import com.example.springbootboardjpa.dto.user.request.UserCreateRequest;
import com.example.springbootboardjpa.dto.user.request.UserUpdateRequest;
import com.example.springbootboardjpa.dto.user.response.UserRepsonse;
import com.example.springbootboardjpa.entity.User;
import com.example.springbootboardjpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserRepsonse createUser(UserCreateRequest userCreateRequest) {
        User user = userRepository.save(userCreateRequest.toEntity());
        return UserRepsonse.fromEntity(user);
    }

    public List<UserRepsonse> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserRepsonse::fromEntity)
                .toList();
    }

    public UserRepsonse findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 회원은 존재하지 않습니다. "));

        return UserRepsonse.fromEntity(user);
    }

    @Transactional
    public UserRepsonse updateUser(Long id, UserUpdateRequest updateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("수정하려는 회원이 존재하지 않습니다."));

        user.updateName(updateRequest.getName());
        user.updateAge(updateRequest.getAge());
        user.updateHobby(updateRequest.getHobby());

        return UserRepsonse.fromEntity(user);
    }


    @Transactional
    public void deleteCustomerById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("삭제하려는 회원을 찾지 못했습니다.");
        }

        userRepository.deleteById(id);
    }
}
