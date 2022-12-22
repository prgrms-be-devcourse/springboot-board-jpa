package devcourse.board.domain.member.model;

import lombok.Getter;

public class MemberRequest {

    private MemberRequest() {
    }

    public static Member toEntity(JoinDto joinDto) {
        return new Member(joinDto.getName(), joinDto.getAge(), joinDto.getHobby());
    }

    @Getter
    public static class JoinDto {
        String name;
        private Integer age;
        private String hobby;

        private JoinDto() {
        }

        public JoinDto(String name) {
            this.name = name;
        }

        public JoinDto(String name, Integer age, String hobby) {
            this.name = name;
            this.age = age;
            this.hobby = hobby;
        }
    }
}
