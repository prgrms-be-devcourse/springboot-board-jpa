package kdt.prgrms.devrun.common.exception;

import kdt.prgrms.devrun.common.enums.ErrorInfo;

public class PostNotFoundException extends RuntimeException {

    private ErrorInfo errorInfo = ErrorInfo.POST_NOT_FOUND;

    public PostNotFoundException() {
        super(ErrorInfo.POST_NOT_FOUND.getMessage());
    }

}
