package kdt.prgms.springbootboard.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SimpleUserDto {

    private Long id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotNull
    @Positive
    private int age;

    @Builder
    public SimpleUserDto(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
