package com.example.board.domain.user.dto;

import com.example.board.domain.hobby.entity.HobbyType;
import com.example.board.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class UserDto {

    public record CreateUserRequest(
            @NotNull(message = "{exception.entity.user.name.null}") @Length(min = 1, message = "{exception.entity.user.name.length}") String name,
            @NotNull(message = "{exception.entity.user.age.null}") @Positive(message = "{exception.entity.user.age.length}") int age,
            @NotNull(message = "{exception.entity.hobby.type.null}") HobbyType hobby) {

        public User toEntity() {
            return User.builder()
                    .name(name)
                    .age(age)
                    .build();
        }
    }

    public record SingleUserDetailResponse(
            @NotNull(message = "{exception.entity.user.name.null}") @Length(min = 1, message = "{exception.entity.user.name.length}") String name,
            @NotNull(message = "{exception.entity.user.age.null}") @Positive(message = "{exception.entity.user.age.length}") int age,
            @NotNull(message = "{exception.entity.hobby.type.null}") List<HobbyType> hobbies) {

        public static SingleUserDetailResponse toResponse(User user, List<HobbyType> userHobbies) {
            return new SingleUserDetailResponse(user.getName(), user.getAge(), userHobbies);
        }
    }

    public record SingleUserSimpleResponse(
            @NotNull(message = "{exception.entity.user.name.null}") @Length(min = 1, message = "{exception.entity.user.name.length}") String name) {

        public static SingleUserSimpleResponse toResponse(User user) {
            return new SingleUserSimpleResponse(user.getName());
        }
    }
}
