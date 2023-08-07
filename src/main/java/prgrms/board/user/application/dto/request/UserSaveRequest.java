package prgrms.board.user.application.dto.request;

import prgrms.board.user.domain.User;

public record UserSaveRequest(String name, Integer age) {
    public User toEntity() {
        return new User(this.name, this.age);
    }
}
