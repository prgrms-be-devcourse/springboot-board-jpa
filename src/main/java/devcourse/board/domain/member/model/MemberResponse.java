package devcourse.board.domain.member.model;

public record MemberResponse(
        String email,
        String name,
        Integer age,
        String hobby
) {

    public MemberResponse(Member member) {
        this(
                member.getEmail(),
                member.getName(),
                member.getAge(),
                member.getHobby()
        );
    }
}
