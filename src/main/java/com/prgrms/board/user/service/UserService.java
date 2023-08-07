package com.prgrms.board.user.service;

import com.prgrms.board.user.domain.User;
import com.prgrms.board.user.domain.UserRepository;
import com.prgrms.board.user.exception.UserNotFoundException;
import com.prgrms.board.user.service.dto.UserDetailedParam;
import com.prgrms.board.user.service.dto.UserServiceConverter;
import com.prgrms.board.user.service.dto.UserShortResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository repository;
    private final UserServiceConverter converter;

    public UserService(UserRepository repository, UserServiceConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Transactional
    public UserShortResult save(UserDetailedParam param) {
        User savingUser = converter.toUser(param);
        User savedUser = repository.save(savingUser);
        return converter.toUserShortResult(savedUser);
    }

    public void validateUserExistence(Long id) {
        if (!repository.existsById(id)) {
            throw new UserNotFoundException("User를 찾을 수 없습니다.");
        }
    }
}
