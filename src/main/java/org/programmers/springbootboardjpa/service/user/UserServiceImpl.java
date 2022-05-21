package org.programmers.springbootboardjpa.service.user;

import lombok.RequiredArgsConstructor;
import org.programmers.springbootboardjpa.domain.user.User;
import org.programmers.springbootboardjpa.repository.user.UserRepository;
import org.programmers.springbootboardjpa.service.exception.NotFoundException;
import org.programmers.springbootboardjpa.web.dto.user.UserCreateForm;
import org.programmers.springbootboardjpa.web.dto.user.UserUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public Long registerUser(UserCreateForm userCreateForm) {
        return userRepository.save(userCreateForm.convertToUser()).getUserId();
    }

    @Override
    public User findUserWith(Long userId) {
        return userRepository.findById(userId).orElseThrow(throwUserIsNotFound(userId));
    }

    private Supplier<NotFoundException> throwUserIsNotFound(Long userId) {
        return () -> new NotFoundException(userId, "User");
    }

    @Transactional
    @Override
    public User modifyUserdata(UserUpdateForm userUpdateForm) {
        User user = userRepository.findById(userUpdateForm.userId()).orElseThrow(throwUserIsNotFound(userUpdateForm.userId()));
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