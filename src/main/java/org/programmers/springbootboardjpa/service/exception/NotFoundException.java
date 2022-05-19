package org.programmers.springbootboardjpa.service.exception;

public class NotFoundException extends IllegalArgumentException {

    public NotFoundException(Long id,  String type) {
        super("지정된 id 값: " + id + " 에 해당하는 " + type + "을(를) 찾을 수 없습니다.");
    }
}