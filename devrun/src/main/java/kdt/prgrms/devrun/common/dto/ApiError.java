package kdt.prgrms.devrun.common.dto;

public class ApiError {

    private String code;

    private Object message;

    public ApiError(String code, Object message) {
        this.code = code;
        this.message = message;
    }

}
