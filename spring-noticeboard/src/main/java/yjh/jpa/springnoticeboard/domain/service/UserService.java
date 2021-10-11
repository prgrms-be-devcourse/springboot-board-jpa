package yjh.jpa.springnoticeboard.domain.service;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yjh.jpa.springnoticeboard.domain.dto.UserDto;
import yjh.jpa.springnoticeboard.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

     Long createUser(UserDto userDto);
     Page<Object> findAll(Pageable pageable);
     UserDto findUser(Long userId) throws NotFoundException;
     Long updateUser(Long userId, UserDto userDto) throws NotFoundException;
     void deleteUser(Long userId);
     void deleteAll();

}
