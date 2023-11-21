package kdt.jpa.board.user.fixture;

import kdt.jpa.board.user.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserFixture {

    public static User getUser() {
        return new User("name", 20, "game");
    }
}
