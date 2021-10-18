package com.prgrms.board.service;

import com.prgrms.board.domain.User;
import com.prgrms.board.dto.IdResponse;
import com.prgrms.board.dto.user.UserCreateRequest;
import com.prgrms.board.dto.user.UserFindRequest;
import com.prgrms.board.error.exception.NotFoundException;
import com.prgrms.board.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public IdResponse createUser(final UserCreateRequest userCreateRequest){
        return IdResponse.from(userRepository.save(userCreateRequest.toEntity()).getId());
    }

    @Transactional(readOnly = true)
    public UserFindRequest findUser(final Long id){
        return UserFindRequest.from(getUser(id));
    }

    @Transactional
    public IdResponse modifyUser(final Long id, final UserCreateRequest userCreateRequest){
        User user = getUser(id);
        user.changeUserInfo(userCreateRequest.getName(), userCreateRequest.getAge(), userCreateRequest.getHobby());

        return IdResponse.from(user.getId());
    }

    @Transactional
    public IdResponse removeUser(Long id) {
        User user = getUser(id);
        userRepository.delete(user);

        return IdResponse.from(user.getId());
    }

    private User getUser(final Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));
    }

}
