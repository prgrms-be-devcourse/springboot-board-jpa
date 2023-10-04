package com.programmers.springbootboardjpa.mapper.user;

import com.programmers.springbootboardjpa.domain.user.User;
import com.programmers.springbootboardjpa.dto.user.UserCreateRequest;
import com.programmers.springbootboardjpa.dto.user.UserResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-01T23:36:09+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserCreateRequest userCreateRequest) {
        if ( userCreateRequest == null ) {
            return null;
        }

        User.UserBuilder<?, ?> user = User.builder();

        user.name( userCreateRequest.getName() );
        user.age( userCreateRequest.getAge() );
        user.hobby( userCreateRequest.getHobby() );

        return user.build();
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.name( user.getName() );
        userResponse.age( user.getAge() );
        userResponse.hobby( user.getHobby() );
        userResponse.createdAt( user.getCreatedAt() );
        userResponse.createdBy( user.getCreatedBy() );

        return userResponse.build();
    }
}
