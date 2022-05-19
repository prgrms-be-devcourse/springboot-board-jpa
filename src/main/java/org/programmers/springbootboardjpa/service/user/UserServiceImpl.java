package org.programmers.springbootboardjpa.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootboardjpa.web.user.dto.user.UserCreateFormV1;
import org.programmers.springbootboardjpa.web.user.dto.user.UserUpdateForm;
import org.programmers.springbootboardjpa.domain.user.User;
import org.programmers.springbootboardjpa.repository.user.UserRepository;
import org.programmers.springbootboardjpa.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public Long registerUser(UserCreateFormV1 userCreateForm) {
        return userRepository.save(userCreateForm.convertToUser()).getUserId();
    }

    @Override
    public User findUserWith(Long userId) {
        return userRepository.findById(userId).orElseThrow(throwUserIsNotFound(userId));
    }

    private Supplier<NotFoundException> throwUserIsNotFound(Long userId) {
        return () -> new NotFoundException(userId, "User");
    }

    //TODO: PR 설명: 버전에 따라 확장하는 지금의 방식 vs 그냥 조회용 Service 따로 만들고 다소 난잡하게 관리하기
    @Transactional
    @Override
    public User modifyUserdata(UserUpdateForm userUpdateForm) {
        log.info("서비스에서 유저 정보 수정 시작: 유저 데이터 찾아오는 중==");
        User user = userRepository.findById(userUpdateForm.id()).orElseThrow(throwUserIsNotFound(userUpdateForm.id()));
        log.info("유저 데이터를 변경하는 중");
        return userUpdateForm.applyToUser(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Page<User> findUsersWithPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}