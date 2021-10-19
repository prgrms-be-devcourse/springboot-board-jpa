package kdt.prgms.springbootboard.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

    private Long id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @Size(max = 255)
    private String content;

    @NotNull
    private UserDto userDto;

    public PostDto() {
    }

    public PostDto(String title, String content, UserDto userDto) {
        this.title = title;
        this.content = content;
        this.userDto = userDto;
    }

    public PostDto(Long id, String title, String content,
        UserDto userDto) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userDto = userDto;
    }
}
