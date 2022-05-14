package org.prgrms.kdt.dto;

public class UserDto {

  public record CurrentUser(Long id, String name, int age){

  }
}