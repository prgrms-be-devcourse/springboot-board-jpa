package kdt.prgrms.devrun.common.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageDto<T> {

    private long totalCount;

    private int pageNo;

    private int pageSize;

    private List<T> list;

    public PageDto(Page<T> tPage) {
        this.totalCount = tPage.getTotalElements();
        this.pageNo = tPage.getNumber();
        this.pageSize = tPage.getSize();
        this.list = tPage.getContent();
    }

}
