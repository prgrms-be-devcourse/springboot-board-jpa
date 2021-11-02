package com.programmers.springbootboard.member.dto.bundle;

import com.programmers.springbootboard.member.domain.vo.Age;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.domain.vo.Hobby;
import com.programmers.springbootboard.member.domain.vo.Name;
import com.programmers.springbootboard.member.dto.request.MemberSignRequest;
import lombok.Getter;

public class MemberBundle {
    public static class Create {
        private Email email;
        private Name name;
        private Age age;
        private Hobby hobby;

        public Create(MemberSignRequest request) {
            this.email = new Email(request.getEmail());
            this.name = new Name(request.getName());
            this.age = new Age(request.getAge());
            this.hobby = new Hobby(request.getHobby());
        }

        public Email getEmail() {
            return email;
        }

        public Name getName() {
            return name;
        }

        public Age getAge() {
            return age;
        }

        public Hobby getHobby() {
            return hobby;
        }
    }
}
