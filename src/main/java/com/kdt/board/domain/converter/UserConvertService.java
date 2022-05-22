package com.kdt.board.domain.converter;

import com.kdt.board.domain.model.User;
import com.kdt.board.domain.repository.UserRepository;
import com.kdt.board.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserConvertService {
    private final UserRepository userRepository;

    @Transactional
    public User entityFindById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity 를 불러오는 중 예외 발생"));
    }
}
