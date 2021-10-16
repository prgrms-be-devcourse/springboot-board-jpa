package kdt.prgms.springbootboard.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

    private Long id;

    private String title;

    private String content;

    private UserDto userDto;

    public PostDto() {
    }

    public PostDto(Long id, String title, String content,
        UserDto userDto) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userDto = userDto;
    }
}
