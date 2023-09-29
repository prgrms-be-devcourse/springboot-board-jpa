package com.blackdog.springbootBoardJpa.domain.user.controller.converter;

import com.blackdog.springbootBoardJpa.domain.user.controller.dto.UserCreateDto;
import com.blackdog.springbootBoardJpa.domain.user.model.vo.Age;
import com.blackdog.springbootBoardJpa.domain.user.model.vo.Name;
import com.blackdog.springbootBoardJpa.domain.user.service.dto.UserCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class UserControllerConverter {

    public UserCreateRequest toRequest(UserCreateDto createDto) {
        return new UserCreateRequest(
                new Name(createDto.name()),
                new Age(createDto.age()),
                createDto.hobby()
        );
    }

}
