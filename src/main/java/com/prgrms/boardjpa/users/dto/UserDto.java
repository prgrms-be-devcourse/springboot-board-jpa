package com.prgrms.boardjpa.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public record UserDto(
  @NotBlank
   Long userId,

  @NotBlank
  @Length(max = 20)
  String name,

  @Min(1)
  @Length(max = 3)
   int age,

  @Length(max = 10)
  String hobby
) {

}
