package com.example.board.domain.common.exception;

public class NotFoundException extends RuntimeException{

  public NotFoundException(String message){
    super(message);
  }
}
