package devcource.hihi.boardjpa.dto.user;

import lombok.Getter;

@Getter
public record UpdateUserDto(String name, int age, String hobby) {
}
