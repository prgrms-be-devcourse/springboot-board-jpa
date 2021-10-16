package com.kdt.bulletinboard.service;

import com.kdt.bulletinboard.converter.PostConverter;
import com.kdt.bulletinboard.dto.UserDto;
import com.kdt.bulletinboard.entity.User;
import com.kdt.bulletinboard.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostConverter postConverter;

    @Transactional
    public Long save(UserDto userDto) {
        User user = postConverter.convertToUser(userDto);
        User saved = userRepository.save(user);
        return saved.getId();
    }

    @Transactional
    public UserDto findOneUser(Long id) throws NotFoundException {
        return userRepository.findById(id)
                .map(postConverter::convertToUserDto)
                .orElseThrow(() -> new NotFoundException("해당 유저를 찾을 수 없습니다."));
    }

    @Transactional
    public Page<UserDto> findAllPost(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(postConverter::convertToUserDto);
    }
}
