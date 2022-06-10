package com.kdt.board.domain.converter;

import com.kdt.board.domain.model.User;
import com.kdt.board.domain.repository.UserRepository;
import com.kdt.board.global.exception.NotFoundEntityByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserConvertService {
    private final UserRepository userRepository;

    @Transactional
    public User entityFindById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityByIdException("Entity 를 찾을 수 없습니다."));
    }
}
