package com.programmers.board.service;

import com.programmers.board.constant.AuthErrorMessage;
import com.programmers.board.dto.service.user.UserDeleteCommand;
import com.programmers.board.dto.service.user.UserGetCommand;
import com.programmers.board.domain.User;
import com.programmers.board.dto.UserDto;
import com.programmers.board.dto.service.user.UserCreateCommand;
import com.programmers.board.dto.service.user.UserUpdateCommand;
import com.programmers.board.exception.AuthorizationException;
import com.programmers.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String USER_NAME_DUPLICATION = "중복된 회원 이름입니다";
    private static final String NO_SUCH_USER = "존재하지 않는 회원입니다";

    private final UserRepository userRepository;

    @Transactional
    public Long createUser(UserCreateCommand command) {
        checkUserNameDuplication(command.getName());
        User user = new User(
                command.getName(),
                command.getAge(),
                command.getHobby()
        );
        userRepository.save(user);
        return user.getId();
    }

    private void checkUserNameDuplication(String name) {
        if (isDuplicatedUserName(name)) {
            throw new DuplicateKeyException(USER_NAME_DUPLICATION);
        }
    }

    @Transactional(readOnly = true)
    public boolean isDuplicatedUserName(String name) {
        return userRepository.findByName(name)
                .isPresent();
    }

    @Transactional(readOnly = true)
    public Page<UserDto> findUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserDto::from);
    }

    @Transactional(readOnly = true)
    public UserDto findUser(UserGetCommand command) {
        User user = findUserOrElseThrow(command.getUserId());
        return UserDto.from(user);
    }

    @Transactional
    public void updateUser(UserUpdateCommand command) {
        User user = findUserOrElseThrow(command.getUserId());
        checkAuthority(command.getLoginUserId(), command.getUserId());
        user.updateMember(command.getName(), command.getAge(), command.getHobby());
    }

    @Transactional
    public void deleteUser(UserDeleteCommand command) {
        User user = findUserOrElseThrow(command.getUserId());
        checkAuthority(command.getLoginUserId(), command.getUserId());
        userRepository.delete(user);
    }

    private User findUserOrElseThrow(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_USER));
        return user;
    }

    private void checkAuthority(Long loginUserId, Long userId) {
        if (notEqualsUserId(loginUserId, userId)) {
            throw new AuthorizationException(AuthErrorMessage.NO_AUTHORIZATION.getMessage());
        }
    }

    private boolean notEqualsUserId(Long loginUserId, Long userId) {
        return !Objects.equals(loginUserId, userId);
    }
}
