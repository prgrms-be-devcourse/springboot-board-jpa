package org.prgrms.board.domain.user.service;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.board.domain.post.domain.Post;
import org.prgrms.board.domain.user.domain.User;
import org.prgrms.board.domain.user.exception.UserException;
import org.prgrms.board.domain.user.mapper.UserMapper;
import org.prgrms.board.domain.user.repository.UserRepository;
import org.prgrms.board.domain.user.requset.UserCreateRequest;
import org.prgrms.board.domain.user.requset.UserLoginRequest;
import org.prgrms.board.domain.user.response.UserSearchResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.prgrms.board.global.exception.ErrorCode.*;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public UserSearchResponse findById(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(USER_NOT_EXIST));
        return userMapper.toSearchResponse(user);
    }

    @Transactional
    public void addPost(long userId, Post post) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(USER_NOT_EXIST));
        user.addPost(post);
    }

    @Transactional
    public long save(UserCreateRequest createRequest) {
        User user = userMapper.toEntity(createRequest);
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Transactional(readOnly = true)
    public long login(UserLoginRequest loginRequest) {
        userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserException(EMAIL_NOT_EXIST));
        User user = userRepository.findByPassword(loginRequest.getPassword())
                .orElseThrow(() -> new UserException(PASSWORD_INCORRECT));
        return user.getId();
    }

    @Transactional
    public long delete(long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserException(USER_NOT_EXIST));
        userRepository.deleteById(userId);
        log.info("Delete user, user id: {}", userId);
        return userId;
    }
}
