package kdt.prgms.springbootboard.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;

    private String name;

    private int age;

    public UserDto() {
    }

    public UserDto(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

}
