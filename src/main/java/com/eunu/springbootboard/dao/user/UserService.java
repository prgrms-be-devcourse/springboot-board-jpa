package com.eunu.springbootboard.dao.user;

import com.eunu.springbootboard.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Transactional
    public String save(UserDto userDto) {
        User user = userConverter.convertUser(userDto);
        User entity = userRepository.save(user);
        return entity.getId();
    }
}
