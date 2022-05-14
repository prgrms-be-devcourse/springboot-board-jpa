package prgrms.project.post.service.user;

import prgrms.project.post.util.validation.MySize;

public record HobbyDto(
        @MySize(max = 20)
        String hobby
) {

}
