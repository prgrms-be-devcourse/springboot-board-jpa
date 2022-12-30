package devcourse.board.domain.member.model;

public record MemberJoinRequest(
        String name,
        Integer age,
        String hobby
) {

    public MemberJoinRequest(String name) {
        this(name, null, null);
    }

    public Member toEntity() {
        return new Member(name, age, hobby);
    }
}
