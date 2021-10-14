package com.prgrms.dlfdyd96.board.user.dto;

import com.prgrms.dlfdyd96.board.common.dto.BaseResponseDto;
import com.prgrms.dlfdyd96.board.post.dto.PostResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse extends BaseResponseDto {

  private final Long id;
  private final String name;
  private final int age;
  private final String hobby; // TODO: 1급 객체
  private final List<PostResponse> postResponses;

  @Builder
  public UserResponse(String createdBy, LocalDateTime createdAt,
      LocalDateTime updatedAt, Long id, String name, int age, String hobby,
      List<PostResponse> postResponses) {
    super(createdBy, createdAt, updatedAt);
    this.id = id;
    this.name = name;
    this.age = age;
    this.hobby = hobby;
    this.postResponses = postResponses;
  }
}
