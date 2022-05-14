package com.prgrms.jpaboard.global.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MetaDataDto {
    private int page;
    private int totalPage;
    private int perPage;
    private long total;

    public MetaDataDto(int page, int totalPage, int perPage, long total) {
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

        public MetaDataDto build() {
            return new MetaDataDto(page, totalPage, perPage, total);
        }
    }
}
