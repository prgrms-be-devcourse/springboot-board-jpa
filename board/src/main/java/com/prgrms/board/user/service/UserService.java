package com.prgrms.board.user.service;

import com.prgrms.board.common.exception.NotFoundException;
import com.prgrms.board.user.domain.User;
import com.prgrms.board.user.dto.UserRequest;
import com.prgrms.board.user.dto.UserResponse;
import com.prgrms.board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static com.prgrms.board.user.dto.UserRequest.toUser;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserResponse insert(UserRequest userRequest) {
        User user = userRepository.save(toUser(userRequest));
        return UserResponse.of(user);
    }

    @Transactional(readOnly = true)
    public UserResponse findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        return UserResponse.of(user);
    }

    @Transactional(readOnly = true)
    public Slice<UserResponse> findAll(Pageable pageable) {
        Slice<User> users = userRepository.findSliceBy(pageable);
        //FIX how to slice to slice
        return new SliceImpl<>(
                users.getContent().stream().map(UserResponse::of).collect(Collectors.toList()),
                pageable,
                users.hasNext()
        );
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

}
