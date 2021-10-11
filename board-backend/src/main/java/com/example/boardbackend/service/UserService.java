package com.example.boardbackend.service;

import com.example.boardbackend.domain.User;
import com.example.boardbackend.domain.embeded.Email;
import com.example.boardbackend.dto.converter.DtoConverter;
import com.example.boardbackend.dto.request.LoginRequest;
import com.example.boardbackend.dto.UserDto;
import com.example.boardbackend.repository.UserRepository;
import javassist.NotFoundException;
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
    private final DtoConverter dtoConverter;

    @Transactional
    public UserDto saveUser(UserDto userDto) {
        User user = dtoConverter.convertToUserEntity(userDto);
        User saved = userRepository.save(user);
        return dtoConverter.convertToUserDto(saved);
    }

    public List<UserDto> findUserAll() {
        return userRepository.findAll().stream()
                .map(user -> dtoConverter.convertToUserDto(user))
                .collect(Collectors.toList());
    }

    public Optional<UserDto> findUserByEmail(String email) {
        // email로 찾기
        Optional<User> byEmail = userRepository.findByEmail(new Email(email));
        // 가입여부 리턴
        if (byEmail.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(dtoConverter.convertToUserDto(byEmail.get()));
        }
    }

    public UserDto findUserById(Long id) throws NotFoundException {
        return userRepository.findById(id)
                .map(user -> dtoConverter.convertToUserDto(user))
                .orElseThrow(() -> new NotFoundException("ID값에 해당하는 사용자가 없음"));
    }

    @Transactional
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

}
