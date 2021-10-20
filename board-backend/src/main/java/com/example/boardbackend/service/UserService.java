package com.example.boardbackend.service;

import com.example.boardbackend.common.error.exception.IllegalArgException;
import com.example.boardbackend.domain.User;
import com.example.boardbackend.domain.embeded.Email;
import com.example.boardbackend.dto.UserDto;
import com.example.boardbackend.common.error.exception.NotFoundException;
import com.example.boardbackend.dto.request.LoginRequest;
import com.example.boardbackend.dto.response.UserIdResponse;
import com.example.boardbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserDto saveUser(UserDto userDto) {
        // Email 중복체크
        Optional<User> byEmail = userRepository.findByEmail(new Email(userDto.getEmail()));
        if(byEmail.isPresent())
            throw new NotFoundException("이미 중복되는 이메일이 있습니다");
        User user = User.of(userDto);
        User saved = userRepository.save(user);
        return UserDto.of(saved);
    }

    public List<UserDto> findUsersAll() {
        return userRepository.findAll().stream()
                .map(UserDto::of)
                .collect(Collectors.toList());
    }

    public UserIdResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Optional<User> byEmail = userRepository.findByEmail(new Email(email));
        // 가입된 email 검증
        if(byEmail.isEmpty())
            throw new NotFoundException("가입되지 않은 이메일입니다.");
        // 비번 검증
        String foundPassword = byEmail.get().getPassword();
        if(!password.equals(foundPassword))
            throw new IllegalArgException("비밀번호가 틀립니다");
        return UserIdResponse.of(byEmail.get().getId());
    }

    public UserDto findUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::of)
                .orElseThrow(() -> new NotFoundException("해당 ID의 사용자가 없음"));
    }

    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

}
