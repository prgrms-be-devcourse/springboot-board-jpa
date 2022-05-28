package com.prgrms.springbootboardjpa.user.service;

import com.prgrms.springbootboardjpa.exception.exceptions.*;
import com.prgrms.springbootboardjpa.user.dto.UserDto;
import com.prgrms.springbootboardjpa.user.dto.UserResponse;
import com.prgrms.springbootboardjpa.user.entity.User;
import com.prgrms.springbootboardjpa.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse save(UserDto userDto){
        User user = UserDto.convertToUser(userDto);
        checkUserDuplicate(user);
        user.setPassword(encodePassword(user.getPassword()));
        User savedUser = userRepository.save(user);
        return UserResponse.convertToUserResponse(savedUser);
    }

    public void checkUserDuplicate(User user){
        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new CustomRuntimeException(CustomExceptionCode.DUPLICATE_EXCEPTION,"Email이 중복됩니다.");

        if (userRepository.findByNickName(user.getNickName()) != null)
            throw new CustomRuntimeException(CustomExceptionCode.DUPLICATE_EXCEPTION,"Nickname이 중복됩니다.");
    }

    public String encodePassword(String password){
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public User login(String email, String password){
        User foundUser = userRepository.findByEmail(email);

        if (foundUser == null){
            throw new CustomRuntimeException(CustomExceptionCode.NO_SUCH_RESOURCE_EXCEPTION, "해당하는 User 정보가 없습니다.");
        }

        if (!checkPassword(foundUser, password))
            throw new CustomRuntimeException(CustomExceptionCode.WRONG_PASSWORD_EXCEPTION);

        return foundUser;
    }

    public boolean checkPassword(User findUser, String password){
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (!passwordEncoder.matches(password,findUser.getPassword())){
            return false;
        }
        return true;
    }

    public Page<UserResponse> findAll(Pageable pageable){
        return userRepository.findAll(pageable).map(user -> UserResponse.convertToUserResponse(user));
    }
}
