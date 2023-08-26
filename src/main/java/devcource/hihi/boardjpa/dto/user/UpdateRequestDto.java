package devcource.hihi.boardjpa.dto.user;

import lombok.Getter;

@Getter
public record UpdateRequestDto(String name, int age, String hobby) {
}
