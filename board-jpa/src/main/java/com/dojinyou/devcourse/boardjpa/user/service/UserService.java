package com.dojinyou.devcourse.boardjpa.user.service;

import com.dojinyou.devcourse.boardjpa.user.entity.User;
import com.dojinyou.devcourse.boardjpa.user.service.dto.UserCreateDto;

public interface UserService {

    void create(UserCreateDto userCreateDto);

    User findById(long id);
}
