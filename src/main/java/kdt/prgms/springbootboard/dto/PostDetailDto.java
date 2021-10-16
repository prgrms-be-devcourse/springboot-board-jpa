package kdt.prgms.springbootboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PostDetailDto {

    private Long id;

    private String title;

    private String content;

    private String createdBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    private String lastModifiedBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastModifiedDate;

    private UserDto userDto;

    public PostDetailDto() {
    }

    public PostDetailDto(Long id, String title, String content, String createdBy,
        LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate,
        UserDto userDto) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.userDto = userDto;
    }

}
