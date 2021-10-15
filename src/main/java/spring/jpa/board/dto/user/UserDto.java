package spring.jpa.board.dto.user;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private Long id;
  private String name;
  private Integer age;
  private String hobby;

  private LocalDateTime createdAt;
  private Long createdBy;
}
