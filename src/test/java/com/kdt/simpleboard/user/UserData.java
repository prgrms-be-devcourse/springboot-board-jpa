package com.kdt.simpleboard.user;
import com.kdt.simpleboard.user.domain.User;
import com.kdt.simpleboard.user.dto.UserRequest;
import com.kdt.simpleboard.user.dto.UserResponse;

public class UserData {
    private static final String NAME = "hyun";
    private static final String HOBBY = "hiking";
    private static final int AGE = 10;
    public static User user(){
        return User.builder()
                .name(NAME)
                .hobby(HOBBY)
                .age(AGE)
                .build();
    }

    public static UserRequest.CreateUserReq createUserReq(){
        return UserRequest.CreateUserReq.builder()
                .name(NAME)
                .hobby(HOBBY)
                .age(AGE)
                .build();
    }

    public static UserResponse.CreateUserRes createUserRes(){
        return UserResponse.CreateUserRes.builder()
                .createdId(1L)
                .build();

    }
}
