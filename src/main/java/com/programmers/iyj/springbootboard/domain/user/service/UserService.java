package com.programmers.iyj.springbootboard.domain.user.service;

import com.programmers.iyj.springbootboard.domain.user.domain.User;
import com.programmers.iyj.springbootboard.domain.user.dto.UserDto;
import com.programmers.iyj.springbootboard.domain.user.mapper.UserMapper;
import com.programmers.iyj.springbootboard.domain.user.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    private final UserRepository userRepository;

    public UserDto findOneById(Long id) throws NotFoundException {
        return userRepository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow(() -> new NotFoundException("No users were found."));
    }

    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(mapper::entityToDto);
    }

    public UserDto save(UserDto userDto) {
        User entity = mapper.dtoToEntity(userDto);
        return mapper.entityToDto(userRepository.save(entity));
    }
}
