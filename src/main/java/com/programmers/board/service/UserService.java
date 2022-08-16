package com.programmers.board.service;

import com.programmers.board.dto.Convertor;
import com.programmers.board.dto.UserRequestDto;
import com.programmers.board.entity.Post;
import com.programmers.board.entity.User;
import com.programmers.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long save(UserRequestDto userRequestDto){
        User user = Convertor.userRequestConvertor(userRequestDto);
        User entity = userRepository.save(user);
        return entity.getId();
    }

}
