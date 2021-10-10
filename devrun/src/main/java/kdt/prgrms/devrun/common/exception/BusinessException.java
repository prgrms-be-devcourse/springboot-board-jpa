package kdt.prgrms.devrun.common.exception;

import kdt.prgrms.devrun.common.enums.ErrorInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private ErrorInfo errorInfo;

    public BusinessException(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

}
