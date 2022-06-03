package com.prgrms.work.controller.dto;

import com.prgrms.work.user.domain.User;
import java.util.Objects;
import javax.validation.constraints.NotNull;

public class MemberRequest {

    public static class MemberCreateDto {

        @NotNull(message = "작성자 이름은 필수입니다.")
        private String name;
        private int age;
        private String hobby;

        public MemberCreateDto(String name, int age, String hobby) {
            this.name = name;
            this.age = age;
            this.hobby = Objects.nonNull(hobby) ? hobby : "없음";
        }

        public String getName() {
            return this.name;
        }

        public int getAge() {
            return this.age;
        }

        public String getHobby() {
            return this.hobby;
        }

        public User toEntity() {
            return User.create(
                this.name,
                this.age,
                this.hobby
            );
        }
    }

}
