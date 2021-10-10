package kdt.prgrms.devrun.common.exception;

import kdt.prgrms.devrun.common.enums.ErrorInfo;

public class UserNotFoundException extends RuntimeException {

    private ErrorInfo errorInfo = ErrorInfo.USER_NOT_FOUND;;

    public UserNotFoundException() {
        super(ErrorInfo.USER_NOT_FOUND.getMessage());
    }

}
