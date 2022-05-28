package com.prgrms.jpaboard.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultDto {
    private Long id;
    private LocalDateTime deletedAt;

    private ResultDto(Long id, LocalDateTime deletedAt) {
        this.id = id;
        this.deletedAt = deletedAt;
    }

    public static ResultDto createResult(Long id) {
        return new ResultDto(id, null);
    }

    public static ResultDto updateResult(Long id) {
        return new ResultDto(id, null);
    }

    public static ResultDto deleteResult(Long id) {
        return new ResultDto(id, LocalDateTime.now());
    }
}
