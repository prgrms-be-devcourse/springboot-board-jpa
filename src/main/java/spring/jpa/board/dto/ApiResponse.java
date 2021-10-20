package spring.jpa.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SelectBeforeUpdate;

@NoArgsConstructor
@Getter
@SelectBeforeUpdate
public class ApiResponse<T> {

  private int statusCode;
  private T data;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime serverDateTime;

  public ApiResponse(int statusCode, T data) {
    this.statusCode = statusCode;
    this.data = data;
    this.serverDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
  }

  public static <T> ApiResponse<T> ok(T data) {
    return new ApiResponse<>(200, data);
  }

  public static <T> ApiResponse<T> fail(int statusCode, T data) {
    return new ApiResponse<>(statusCode, data);
  }
}
