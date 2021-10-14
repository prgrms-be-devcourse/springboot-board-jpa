package com.example.board.service;

import com.example.board.converter.Converter;
import com.example.board.dto.UserDto;
import com.example.board.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Converter postConverter;

    @Autowired
    UserService(UserRepository userRepository, Converter postConverter){
        this.userRepository = userRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(postConverter::convertUserEntity);
    }


    @Transactional
    public UserDto find(String name) throws NotFoundException {
        return userRepository.findByName(name)
                .map(postConverter::convertUserEntity)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));
    }

    @Transactional
    public Integer save(UserDto userDto) {
        return Integer.valueOf(userRepository.save(postConverter.convertUser(userDto)).getId());
    }
}
