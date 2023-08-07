package prgrms.board.user.application.dto.response;

import prgrms.board.user.domain.User;

public record UserSaveResponse(
        Long userId,
        String name,
        Integer age
) {
    public static UserSaveResponse of(User user) {
        long savedId = user.getId();
        String savedName = user.getName();
        int savedAge = user.getAge();

        return new UserSaveResponse(savedId, savedName, savedAge);
    }
}
