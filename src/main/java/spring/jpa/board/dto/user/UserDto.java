package spring.jpa.board.dto.user;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserDto {

  private Long id;
  private String name;
  private Integer age;
  private String hobby;

  private LocalDateTime createdAt;
  private Long createdBy;
}
