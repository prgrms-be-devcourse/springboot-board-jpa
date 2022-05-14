package com.prgrms.jpaboard.global.common.resquest;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class PageParamDto {
    @Min(0)
    @NotNull
    private int page;

    @Min(1)
    @NotNull
    private int perPage;

    public PageParamDto(int page, int perPage) {
        this.page = page;
        this.perPage = perPage;
    }
}
