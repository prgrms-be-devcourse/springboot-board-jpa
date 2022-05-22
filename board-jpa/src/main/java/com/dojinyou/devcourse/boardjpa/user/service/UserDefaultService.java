package com.dojinyou.devcourse.boardjpa.user.service;

import com.dojinyou.devcourse.boardjpa.common.exception.NotFoundException;
import com.dojinyou.devcourse.boardjpa.user.entity.User;
import com.dojinyou.devcourse.boardjpa.user.repository.UserRepository;
import com.dojinyou.devcourse.boardjpa.user.service.dto.UserCreateDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDefaultService implements UserService {

    private final UserRepository userRepository;

    public UserDefaultService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void create(UserCreateDto userCreateDto) {
        if (userCreateDto == null) {
            throw new IllegalArgumentException("유저 생성을 위한 데이터 전달 객체는 빈 객체일 수 없습니다");
        }
        User newUser = User.builder()
                           .name(userCreateDto.getName())
                           .age(userCreateDto.getAge())
                           .hobby(userCreateDto.getHobby())
                           .build();

        userRepository.save(newUser);
    }

    @Override
    public User findById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
