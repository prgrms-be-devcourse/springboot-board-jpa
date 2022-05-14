package prgrms.project.post.service.user;

import lombok.Builder;
import prgrms.project.post.util.validation.MySize;

import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

public record UserDto(
        Long id,
        @MySize(max = 100)
        String name,
        @PositiveOrZero
        int age,
        Set<HobbyDto> hobbies
) {

    @Builder
    public UserDto {
    }
}
