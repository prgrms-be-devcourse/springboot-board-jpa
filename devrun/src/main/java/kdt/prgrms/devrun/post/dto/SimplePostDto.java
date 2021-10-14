package kdt.prgrms.devrun.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
public class SimplePostDto {

    @NotBlank
    private Long id;

    @NotBlank
    private String createdBy;

    @NotBlank
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

}
