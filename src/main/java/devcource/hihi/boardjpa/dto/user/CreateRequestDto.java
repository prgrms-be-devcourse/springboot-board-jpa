package devcource.hihi.boardjpa.dto.user;

import devcource.hihi.boardjpa.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
public record CreateRequestDto(@NotNull String name, @NotNull Integer age, String hobby) {

    public User toEntity() {
        return User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }
}