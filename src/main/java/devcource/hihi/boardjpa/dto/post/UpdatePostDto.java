package devcource.hihi.boardjpa.dto.post;

import lombok.Getter;

@Getter
public record UpdatePostDto(String title, String content) {
}
