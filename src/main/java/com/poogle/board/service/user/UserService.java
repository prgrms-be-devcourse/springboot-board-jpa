package com.poogle.board.service.user;

import com.poogle.board.controller.user.UserRequest;
import com.poogle.board.error.NotFoundException;
import com.poogle.board.model.user.User;
import com.poogle.board.repository.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User join(User user) {
        return insert(user);
    }

    @Transactional(readOnly = true)
    public Page<User> findUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUser(Long id) {
        return userRepository.findById(id);
    }

    public User modify(Long id, UserRequest user) {
        User foundUser = findUserById(id);
        foundUser.update(user.getName(), user.getAge(), user.getHobby());
        return insert(foundUser);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, id));
    }

    private User insert(User user) {
        return userRepository.save(user);
    }

}
