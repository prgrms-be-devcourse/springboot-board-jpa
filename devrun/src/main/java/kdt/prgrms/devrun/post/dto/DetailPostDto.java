package kdt.prgrms.devrun.post.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
public class DetailPostDto {

    @NotBlank
    private Long id;

    @NotBlank
    private String title;

    private String content;

    @NotBlank
    private String createdBy;

    private LocalDateTime createdAt;

}
