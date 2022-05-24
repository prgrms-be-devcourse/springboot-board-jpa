package com.prgrms.springbootboardjpa.user.dto;

import com.prgrms.springbootboardjpa.user.entity.Email;
import com.prgrms.springbootboardjpa.user.entity.Name;
import com.prgrms.springbootboardjpa.user.entity.Password;
import com.prgrms.springbootboardjpa.user.entity.User;
import com.sun.istack.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto{

    private Long id;

    @NotNull
    private String nickName;
    private int age;
    private String hobby;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String password;
    @NotNull
    private String email;

    public static User convertToUser(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setNickName(userDto.getNickName());
        user.setAge(userDto.getAge());
        user.setHobby(userDto.getHobby());

        user.setName(new Name(userDto.getFirstName(), userDto.getLastName()));
        user.setPassword(new Password(userDto.getPassword()));
        user.setEmail(new Email(userDto.getEmail()));

        user.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        user.setCreatedBy(user.getName().toString());

        return user;
    }

    public static UserDto convertToUserDto(User user){
        return new UserDto(user.getId(),
                user.getNickName(),
                user.getAge(),
                user.getHobby(),
                user.getName().getFirstName(),
                user.getName().getLastName(),
                user.getPassword().getPassword(),
                user.getEmail().getEmail());
    }
}
