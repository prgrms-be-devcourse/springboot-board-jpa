package org.prgrms.kdt.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PostDto {

  public record PostRequest(
      @NotBlank
      @Size(max = 30)
      String title,
      @NotBlank
      String content
  ) {

  }

  public record PostResponse(
      Long id,
      String title,
      String content,
      String createdBy,
      LocalDateTime createdAt,
      LocalDateTime updatedAt
  ) {

  }
}