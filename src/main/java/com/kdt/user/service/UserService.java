package com.kdt.user.service;

import com.kdt.user.converter.UserConverter;
import com.kdt.user.dto.UserDto;
import com.kdt.user.model.User;
import com.kdt.user.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    private static final String NOT_FOUNDED_MESSAGE = "this account is not valid";

    public Long save(UserDto userDto){
        User user = userRepository.save(userConverter.convertUserDto(userDto));
        return user.getId();
    }

    public Long update(Long id, UserDto userDto) throws NotFoundException
    {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUNDED_MESSAGE));
        user.update(userDto.getName(), userDto.getAge(), userDto.getHobby());
        return user.getId();
    }

    public void delete(Long id) throws NotFoundException{
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUNDED_MESSAGE));

        userRepository.deleteById(id);
    }

    public UserDto find(Long id) throws NotFoundException{
        return userRepository.findById(id)
                .map(userConverter::convertUser)
                .orElseThrow(() -> new NotFoundException(NOT_FOUNDED_MESSAGE));
    }


}
