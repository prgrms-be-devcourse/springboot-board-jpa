package kdt.prgrms.devrun.common.exception;

import kdt.prgrms.devrun.common.enums.ErrorInfo;

public class DuplicatedLoginIdException extends BusinessException {
    public DuplicatedLoginIdException() {
        super(ErrorInfo.DUPLICATE_LOGIN_ID);
    }
}
