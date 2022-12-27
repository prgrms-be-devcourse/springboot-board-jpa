package devcourse.board.domain.member.model;

public record MemberJoinDto(
        String name,
        Integer age,
        String hobby
) {

    public MemberJoinDto(String name) {
        this(name, null, null);
    }

    public Member toEntity() {
        return new Member(name, age, hobby);
    }
}
