package kdt.prgms.springbootboard.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostSaveRequestDto {

    private Long id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @Size(max = 255)
    private String content;

    @NotNull
    @JsonProperty("user")
    private SimpleUserDto simpleUserDto;

    @Builder
    public PostSaveRequestDto(Long id, String title, String content,
        SimpleUserDto simpleUserDto) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.simpleUserDto = simpleUserDto;
    }
}
