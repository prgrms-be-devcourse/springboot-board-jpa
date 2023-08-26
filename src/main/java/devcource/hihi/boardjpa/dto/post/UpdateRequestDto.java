package devcource.hihi.boardjpa.dto.post;

import lombok.Getter;

@Getter
public record UpdateRequestDto(String title, String content) {
}
