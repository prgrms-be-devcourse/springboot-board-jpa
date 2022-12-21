package com.prgrms.boardjpa.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  @NotBlank
  private Long userId;

  @NotBlank
  @Length(max = 20)
  private String name;

  @Min(1)
  @Length(max = 3)
  private int age;

  private String hobby;
}
