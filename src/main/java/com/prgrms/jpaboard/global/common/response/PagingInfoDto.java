package com.prgrms.jpaboard.global.common.response;

import lombok.Getter;

@Getter
public class PagingInfoDto {
    private int page;
    private int totalPage;
    private int perPage;
    private long total;

    public PagingInfoDto(int page, int totalPage, int perPage, long total) {
        this.page = page;
        this.totalPage = totalPage;
        this.perPage = perPage;
        this.total = total;
    }

    public static MetaDataDtoBuilder builder() {
        return new MetaDataDtoBuilder();
    }

    public static class MetaDataDtoBuilder {
        private int page;
        private int totalPage;
        private int perPage;
        private long total;

        public MetaDataDtoBuilder page(int page) {
            this.page = page;
            return this;
        }

        public MetaDataDtoBuilder totalPage(int totalPage) {
            this.totalPage = totalPage;
            return this;
        }

        public MetaDataDtoBuilder perPage(int perPage) {
            this.perPage = perPage;
            return this;
        }

        public MetaDataDtoBuilder total(long total) {
            this.total = total;
            return this;
        }

        public PagingInfoDto build() {
            return new PagingInfoDto(page, totalPage, perPage, total);
        }
    }
}
