package org.prgms.board.user.service;

import org.prgms.board.common.exception.NotFoundException;
import org.prgms.board.domain.entity.User;
import org.prgms.board.domain.repository.UserRepository;
import org.prgms.board.user.dto.UserRequest;
import org.prgms.board.user.dto.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long addUser(UserRequest userRequest) {
        return userRepository.save(userRequest.toEntity()).getId();
    }

    @Transactional
    public Long modifyUser(Long userId, UserRequest userRequest) {
        User user = getUser(userId);
        user.changeInfo(userRequest.getName(), userRequest.getAge(), userRequest.getHobby());
        return user.getId();
    }

    @Transactional
    public Long removeUser(Long userId) {
        User user = getUser(userId);
        user.removeUser();
        return user.getId();
    }

    @Transactional(readOnly = true)
    public UserResponse getOneUser(Long userId) {
        return new UserResponse(getUser(userId));
    }

    private User getUser(Long id) {
        return userRepository.findByIdAndDeleted(id, false)
            .orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));
    }
}
