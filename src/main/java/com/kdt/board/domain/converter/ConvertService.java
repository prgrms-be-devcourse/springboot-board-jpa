package com.kdt.board.domain.converter;

import com.kdt.board.domain.model.User;
import com.kdt.board.domain.repository.PostRepository;
import com.kdt.board.domain.repository.UserRepository;
import com.kdt.board.global.exception.LoadEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConvertService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public User entityFindById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new LoadEntityException("Entity 를 불러오는 중 예외 발생"));
    }
}
