package com.prgrms.java.service;

import com.prgrms.java.domain.Email;
import com.prgrms.java.domain.LoginState;
import com.prgrms.java.domain.User;
import com.prgrms.java.dto.user.GetUserDetailsResponse;
import com.prgrms.java.dto.user.PostLoginRequest;
import com.prgrms.java.dto.user.PostUserRequest;
import com.prgrms.java.exception.AuthenticationFailedException;
import com.prgrms.java.exception.ResourceNotFountException;
import com.prgrms.java.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void joinUser(PostUserRequest postUserRequest) {
        User user = postUserRequest.toEntity();
        userRepository.save(user);
    }

    public LoginState loginUser(PostLoginRequest postLoginRequest) {
        User user = findByEmail(postLoginRequest.email());

        if (user.getPassword().equals(postLoginRequest.password())) {
            return LoginState.SUCCESS;
        } else {
            return LoginState.FAIL;
        }
    }

    public GetUserDetailsResponse getUserDetails(Email email) {
        User user = findByEmail(email.value());

        return GetUserDetailsResponse.from(user);
    }

    private User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFountException(MessageFormat.format("Can not find User. Please check email. [Email]: {0}", email)));
    }

    public void validMember(Email email) {
        Long userCountByEmail = userRepository.countUserByEmail(email.value());
        if (userCountByEmail < 1L) {
            throw new AuthenticationFailedException(MessageFormat.format("Can not find User by given email. [Email]: {0}", email));
        }
    }
}
