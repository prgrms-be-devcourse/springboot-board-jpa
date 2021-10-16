package kdt.prgms.springbootboard.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotNull
    @Positive
    private int age;

    public UserDto() {
    }

    public UserDto(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

}
