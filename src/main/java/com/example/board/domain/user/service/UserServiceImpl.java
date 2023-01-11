package com.example.board.domain.user.service;

import static com.example.board.domain.user.dto.UserDto.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.domain.hobby.entity.Hobby;
import com.example.board.domain.hobby.entity.HobbyType;
import com.example.board.domain.hobby.repository.HobbyRepository;
import com.example.board.domain.user.entity.User;
import com.example.board.domain.user.repository.UserRepository;
import com.example.board.domain.userhobby.entity.UserHobby;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final HobbyRepository hobbyRepository;

    @Transactional
    @Override
    public SingleUserDetailResponse enroll(CreateUserRequest createUserRequest) {
        User user = createUserRequest.toEntity();

        Hobby findHobby = hobbyRepository.findByHobbyType(createUserRequest.hobby());
        UserHobby userHobby = UserHobby.builder()
                .user(user)
                .hobby(findHobby)
                .build();
        user.addHobby(userHobby);
        User savedUser = userRepository.save(user);
        List<HobbyType> userHobbies = loadUserHobbyTypes(user);
        return SingleUserDetailResponse.toResponse(savedUser, userHobbies);
    }

    private static List<HobbyType> loadUserHobbyTypes(User user) {
        return user.getUserHobbies()
                .stream()
                .map(UserHobby::getHobby)
                .map(Hobby::getHobbyType)
                .toList();
    }
}
