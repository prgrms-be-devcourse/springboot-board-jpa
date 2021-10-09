package com.devcourse.bbs.service.user;

import com.devcourse.bbs.controller.bind.UserCreateRequest;
import com.devcourse.bbs.controller.bind.UserUpdateRequest;
import com.devcourse.bbs.domain.user.User;
import com.devcourse.bbs.domain.user.UserDTO;
import com.devcourse.bbs.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<UserDTO> findUserByName(String name) {
        return userRepository.findByName(name).map(User::toDTO);
    }

    @Override
    public UserDTO createUser(UserCreateRequest request) {
        if(userRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Duplicated username cannot be submitted.");
        }
        User user = User.builder()
                .name(request.getName())
                .age(request.getAge())
                .hobby(request.getHobby()).build();
        user = userRepository.save(user);
        return user.toDTO();
    }

    @Override
    public UserDTO updateUser(String name, UserUpdateRequest request) {
        User user = userRepository.findByName(name).orElseThrow(() -> {
            throw new IllegalArgumentException("User with given username not found.");
        });
        user.updateName(request.getName());
        user.updateAge(request.getAge());
        return user.toDTO();
    }

    @Override
    public void deleteUser(String name) {
        userRepository.deleteByName(name);
    }
}
