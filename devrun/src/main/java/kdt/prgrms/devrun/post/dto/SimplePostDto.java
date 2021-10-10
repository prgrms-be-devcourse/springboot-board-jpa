package kdt.prgrms.devrun.post.dto;

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

    private LocalDateTime createdAt;

}
