package devcourse.board.domain.member.model;

import javax.validation.constraints.NotBlank;

public record MemberJoinRequest(
        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotBlank
        String name,

        Integer age,

        String hobby
) {

    public MemberJoinRequest(String email, String password, String name) {
        this(email, password, name, null, null);
    }

    public Member toEntity() {
        return Member.create(email, password, name, age, hobby);
    }
}
