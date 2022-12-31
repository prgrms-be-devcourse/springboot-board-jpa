package com.prgrms.springbootboardjpa.service;

import com.prgrms.springbootboardjpa.dto.UserRequest;
import com.prgrms.springbootboardjpa.dto.DtoMapper;
import com.prgrms.springbootboardjpa.dto.UserResponse;
import com.prgrms.springbootboardjpa.entity.User;
import com.prgrms.springbootboardjpa.exception.NotFoundException;
import com.prgrms.springbootboardjpa.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    private final DtoMapper dtoMapper;

    public UserService(UserRepository userRepository, DtoMapper dtoMapper) {
        this.userRepository = userRepository;
        this.dtoMapper = dtoMapper;
    }

    @Transactional
    public long save(UserRequest userRequest) {
        User user = dtoMapper.convertUser(userRequest);

        User entity = userRepository.save(user);

        return entity.getId();
    }

    @Transactional(readOnly = true)
    public UserResponse getOne(long id) {
        return userRepository.findById(id)
                .map(dtoMapper::convertUser)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(dtoMapper::convertUser);
    }

    @Transactional
    public UserResponse update(long id, UserRequest userRequest) {
        return dtoMapper.convertUser(
                userRepository.findById(id)
                        .map(user -> {
                            if (userRequest.getName() != null && !userRequest.getName().equals(user.getName())) user.setName(userRequest.getName());
                            if (userRequest.getAge() != null && !userRequest.getAge().equals(user.getAge())) user.setAge(userRequest.getAge());
                            if (userRequest.getHobby() != null && !userRequest.getHobby().equals(user.getHobby())) user.setHobby(userRequest.getHobby());
                            return userRepository.save(user);
                        })
                        .orElseThrow(NotFoundException::new));
    }

    @Transactional
    public void delete(long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.debug("Cannot found user for {}", id);
        }
    }
}
