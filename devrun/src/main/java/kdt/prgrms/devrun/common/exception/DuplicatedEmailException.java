package kdt.prgrms.devrun.common.exception;

import kdt.prgrms.devrun.common.enums.ErrorInfo;

public class DuplicatedEmailException extends BusinessException {
    public DuplicatedEmailException() {
        super(ErrorInfo.DUPLICATE_EMAIL);
    }
}
