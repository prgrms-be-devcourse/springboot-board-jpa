package com.prgrms.springbootboardjpa.user.dto;

import com.prgrms.springbootboardjpa.Patterns;
import com.prgrms.springbootboardjpa.user.entity.Name;
import com.prgrms.springbootboardjpa.user.entity.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto{

    private Long id;

    @NotNull
    @Size(min = 2, max = 30)
    private String nickName;

    @Min(1)
    private int age;

    @Size(max = 50)
    private String hobby;

    @NotNull
    @Pattern(regexp = Patterns.NAME_PATTERN)
    @Size(min = 1, max = 30)
    private String firstName;

    @NotNull
    @Pattern(regexp = Patterns.NAME_PATTERN)
    @Size(min = 1, max = 30)
    private String lastName;

    @NotNull
    @Pattern(regexp = Patterns.PASSWD_PATTERN)
    @Size(min = 8, max = 100)
    private String password;


    @NotNull
    @Pattern(regexp = Patterns.EMAIL_PATTERN)
    @Size(min = 5, max = 50)
    private String email;

    public static User convertToUser(UserDto userDto){
        User user = new User.UserBuilder()
                .id(userDto.getId())
                .nickName(userDto.getNickName())
                .age(userDto.getAge())
                .hobby(userDto.getHobby())
                .name(Name.builder()
                        .firstName(userDto.getFirstName())
                        .lastName(userDto.getLastName())
                        .build())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS))
                .createdBy(Name.builder()
                        .firstName(userDto.getFirstName())
                        .lastName(userDto.getLastName())
                        .build().toString())
                .build()
                ;

        return user;
    }

    public static UserDto convertToUserDto(User user){
        return new UserDto(user.getId(),
                user.getNickName(),
                user.getAge(),
                user.getHobby(),
                user.getName().getFirstName(),
                user.getName().getLastName(),
                user.getPassword(),
                user.getEmail());
    }
}
