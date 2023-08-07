package devcource.hihi.boardjpa.dto.user;

import lombok.Getter;

@Getter
public record ResponseUserDto (Long id,String name,Integer age,String hobby){}