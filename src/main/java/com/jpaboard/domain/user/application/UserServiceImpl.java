package com.jpaboard.domain.user.application;

import com.jpaboard.domain.user.User;
import com.jpaboard.domain.user.UserConverter;
import com.jpaboard.domain.user.dto.UserCreationRequest;
import com.jpaboard.domain.user.dto.UserUpdateRequest;
import com.jpaboard.domain.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Long save(UserCreationRequest request) {
        User savedUser = UserConverter.convertRequestToEntity(request);
        userRepository.save()
    }

    @Override
    public User findById(Long id) {

        return;
    }

    @Override
    public void updateById(Long id, UserUpdateRequest request) {

    }

    @Override
    public void deleteById(Long id) {

    }
//    void update(Long id, UserUpdateRequest request) {
//        User found = findbyId(id)
//        requestUser 생성 시점부터 id값을 증가시켜버려서 (mysql에 키를 생성)
//        found.update(request);
////        or
////        found.update(requestUser);// controller -> service -> domain
//    }
}
