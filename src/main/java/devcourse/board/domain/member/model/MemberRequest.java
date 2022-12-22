package devcourse.board.domain.member.model;

import lombok.Getter;

public class MemberRequest {

    private MemberRequest() {
    }

    public static Member toEntity(JoinDto joinDto) {
        if (joinDto instanceof SpecificJoinDto specificJoinDto) {
            return new Member(specificJoinDto.getName(), specificJoinDto.getAge(), specificJoinDto.getHobby());
        }
        return new Member(joinDto.getName());
    }

    @Getter
    public static class JoinDto {
        String name;

        private JoinDto() {
        }

        public JoinDto(String name) {
            this.name = name;
        }
    }

    @Getter
    public static class SpecificJoinDto extends JoinDto {
        private int age;
        private String hobby;

        private SpecificJoinDto() {
        }

        public SpecificJoinDto(String name, int age, String hobby) {
            super.name = name;
            this.age = age;
            this.hobby = hobby;
        }
    }
}
