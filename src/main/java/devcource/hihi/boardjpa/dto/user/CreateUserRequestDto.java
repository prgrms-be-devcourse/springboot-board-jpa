package devcource.hihi.boardjpa.dto.user;

import devcource.hihi.boardjpa.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateUserRequestDto(@NotNull String name, @NotNull Integer age, String hobby) {

    public User toEntity() {
        return User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }
}