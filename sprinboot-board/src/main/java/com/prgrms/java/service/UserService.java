package com.prgrms.java.service;

import com.prgrms.java.domain.Email;
import com.prgrms.java.domain.LoginState;
import com.prgrms.java.domain.User;
import com.prgrms.java.dto.user.GetUserDetailsResponse;
import com.prgrms.java.dto.user.LoginRequest;
import com.prgrms.java.dto.user.CreateUserRequest;
import com.prgrms.java.exception.AuthenticationFailedException;
import com.prgrms.java.exception.ResourceNotFountException;
import com.prgrms.java.repository.UserRepository;
import com.prgrms.java.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long joinUser(CreateUserRequest createUserRequest) {
        final User user = createUserRequest.toEntity();
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public Long loginUser(LoginRequest loginRequest) {
        final User user = findByEmail(loginRequest.email());
        user.login(loginRequest.password());
        return user.getId();
    }

    public GetUserDetailsResponse getUserDetails(Email email) {
        validMember(email);
        final User user = findByEmail(email.value());
        return GetUserDetailsResponse.from(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFountException(MessageFormat.format("Can not find User. Please check email. [Email]: {0}", email)));
    }

    private void validMember(Email email) {
        if (!userRepository.existsUserByEmail(email.value())) {
            throw new AuthenticationFailedException(MessageFormat.format("Can not find User by given email. [Email]: {0}", email));
        }
    }

    public void authenticateUser(HttpServletRequest httpServletRequest) {
        final Email email = CookieUtil.getLoginToken(httpServletRequest);
        validMember(email);
    }
}
