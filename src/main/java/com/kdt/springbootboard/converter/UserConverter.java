package com.kdt.springbootboard.converter;

import com.kdt.springbootboard.domain.user.User;
import com.kdt.springbootboard.domain.user.vo.Age;
import com.kdt.springbootboard.domain.user.vo.Email;
import com.kdt.springbootboard.domain.user.vo.Hobby;
import com.kdt.springbootboard.domain.user.vo.Name;
import com.kdt.springbootboard.dto.user.UserCreateRequest;
import com.kdt.springbootboard.dto.user.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    // Todo : 모든 Dto와 Vo 그리고 Converter클래스에서 어색하거나 효율적이지 못한 부분?

    public User convertToUser(UserCreateRequest dto) {
        return User.builder()
            .name(new Name(dto.getName()))
            .email(new Email(dto.getEmail()))
            .age(new Age(dto.getAge()))
            .hobby(new Hobby(dto.getHobby()))
            .build();
    }

    public UserResponse convertToUserResponse(User user) { // Todo : Api마다 다른 Response dto를 만들어 줘야 함?
        return UserResponse.builder()
            .id(user.getId())
            .name(user.getName().getName())
            .email(user.getEmail().getEmail())
            .age(user.getAge().getAge())
            .hobby(user.getHobby().getHobby())
            .build();
    }
}
