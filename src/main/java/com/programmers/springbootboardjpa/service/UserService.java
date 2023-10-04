package com.programmers.springbootboardjpa.service;

import static com.programmers.springbootboardjpa.global.exception.rule.UserRule.USER_NOT_FOUND_FOR_DELETE;
import static com.programmers.springbootboardjpa.global.exception.rule.UserRule.USER_NOT_FOUND_FOR_FIND;
import static com.programmers.springbootboardjpa.global.exception.rule.UserRule.USER_NOT_FOUND_FOR_UPDATE;

import com.programmers.springbootboardjpa.domain.user.User;
import com.programmers.springbootboardjpa.dto.user.UserCreateRequest;
import com.programmers.springbootboardjpa.dto.user.UserResponse;
import com.programmers.springbootboardjpa.dto.user.UserUpdateRequest;
import com.programmers.springbootboardjpa.global.exception.UserException;
import com.programmers.springbootboardjpa.global.exception.rule.UserRule;
import com.programmers.springbootboardjpa.mapper.user.UserMapper;
import com.programmers.springbootboardjpa.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public void save(UserCreateRequest userCreateRequest) {
        User user = userMapper.toUser(userCreateRequest);
        user.setCreatedBy(user.getName());

        userRepository.save(user);
    }

    public UserResponse findById(Long id) {
        User user = findById(id, USER_NOT_FOUND_FOR_FIND);

        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAllByOrderByNameAsc(pageable)
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id, USER_NOT_FOUND_FOR_DELETE);

        userRepository.deleteById(id);
    }

    @Transactional
    public void updateById(Long id, UserUpdateRequest userUpdateRequest) {
        User user = findById(id, USER_NOT_FOUND_FOR_UPDATE);

        user.updateName(userUpdateRequest.getName());
        user.updateHobby(userUpdateRequest.getHobby());
    }

    private User findById(Long id, UserRule userRule) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException(userRule));
    }

}
