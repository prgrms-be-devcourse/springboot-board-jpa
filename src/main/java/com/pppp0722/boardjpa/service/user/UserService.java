package com.pppp0722.boardjpa.service.user;

import com.pppp0722.boardjpa.web.dto.UserRequestDto;
import com.pppp0722.boardjpa.web.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponseDto save(UserRequestDto userRequestDto);

    UserResponseDto findById(Long id);

    Page<UserResponseDto> findAll(Pageable pageable);

    UserResponseDto update(Long id, UserRequestDto userRequestDto);

    void deleteById(Long id);
}
