package com.prgrms.jpa.exception;

public enum ExceptionMessage {
     ENTITY_NOT_FOUND("존재하지 않는 %s 입니다.");

     private final String message;

     ExceptionMessage(String message) {
          this.message = message;
     }
}
