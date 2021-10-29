package com.kdt.springbootboard.service;

import com.kdt.springbootboard.converter.UserConverter;
import com.kdt.springbootboard.domain.user.User;
import com.kdt.springbootboard.domain.user.vo.Age;
import com.kdt.springbootboard.domain.user.vo.Email;
import com.kdt.springbootboard.domain.user.vo.Hobby;
import com.kdt.springbootboard.domain.user.vo.Name;
import com.kdt.springbootboard.dto.user.UserCreateRequest;
import com.kdt.springbootboard.dto.user.UserResponse;
import com.kdt.springbootboard.dto.user.UserUpdateRequest;
import com.kdt.springbootboard.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Transactional
    public Long insert(UserCreateRequest dto) {
        User user = userConverter.convertToUser(dto);
        userRepository.save(user);
        user.setInfo(user.getId());
        return user.getId();
    }

    public UserResponse findById(Long id) throws NotFoundException {
        return userRepository.findById(id)
            .map(userConverter::convertToUserResponse)
            .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));
    }

    public Page<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
            .map(userConverter::convertToUserResponse);
    }

    @Transactional
    public UserResponse update(UserUpdateRequest dto) throws NotFoundException {
        User user = userRepository.findById(dto.getId())
            .orElseThrow(() -> new NotFoundException("업데이트할 유저를 찾을 수 없습니다."));
        user.update(new Name(dto.getName()), new Email(dto.getEmail()), new Age(dto.getAge()), new Hobby(dto.getHobby()));
        userRepository.save(user); // Todo : save 해야함 ? 변경감지 확인하기
        return userConverter.convertToUserResponse(user);
    }

    @Transactional
    public Long delete(Long id) throws NotFoundException {
        userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("삭제할 유저를 찾을 수 없습니다."));
        userRepository.deleteById(id);
        return id;
    }

}
