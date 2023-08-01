package com.programmers.springbootboardjpa.service;

import com.programmers.springbootboardjpa.domain.user.User;
import com.programmers.springbootboardjpa.dto.user.UserCreateRequest;
import com.programmers.springbootboardjpa.dto.user.UserResponse;
import com.programmers.springbootboardjpa.dto.user.UserUpdateRequest;
import com.programmers.springbootboardjpa.mapper.user.UserMapper;
import com.programmers.springbootboardjpa.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
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
        User user = findById(id, "찾으시려는 아이디가 없습니다.");

        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAllByOrderByNameAsc(pageable)
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id, "삭제하려는 아이디가 없습니다.");

        userRepository.deleteById(id);
    }

    @Transactional
    public void updateById(Long id, UserUpdateRequest userUpdateRequest) {
        User user = findById(id, "업데이트하려는 아이디가 없습니다.");

        user.updateName(userUpdateRequest.getName());
        user.updateHobby(userUpdateRequest.getHobby());
    }

    private User findById(Long id, String exceptionMessage) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(exceptionMessage));
    }

}
