package jehs.springbootboardjpa.dto;

import jehs.springbootboardjpa.entity.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final String name;
    private final Long age;
    private final String hobby;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
        this.hobby = user.getHobby();
    }
}
