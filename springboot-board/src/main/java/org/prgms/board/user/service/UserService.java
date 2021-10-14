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
        User user = activeUser(userId);
        user.changeInfo(userRequest.getName(), userRequest.getAge(), userRequest.getHobby());
        return user.getId();
    }

    @Transactional
    public void removeUser(Long userId) {
        User user = activeUser(userId);
        user.removeUser();
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(Long userId) {
        return new UserResponse(activeUser(userId));
    }

    private User activeUser(Long id) {
        return userRepository.findByIdAndDeleted(id, false)
            .orElseThrow(() -> new NotFoundException(String.format("해당 %d번 사용자를 찾을 수 없습니다.", id)));
    }
}
