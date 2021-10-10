package kdt.prgrms.devrun.common.exception;

import kdt.prgrms.devrun.common.enums.ErrorInfo;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() {
        super(ErrorInfo.USER_NOT_FOUND);
    }

}
