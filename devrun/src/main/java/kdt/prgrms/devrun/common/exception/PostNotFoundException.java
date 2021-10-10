package kdt.prgrms.devrun.common.exception;

import kdt.prgrms.devrun.common.enums.ErrorInfo;

public class PostNotFoundException extends BusinessException {

    public PostNotFoundException() {
        super(ErrorInfo.POST_NOT_FOUND);
    }

}
