package org.programmers.springbootboardjpa.web.dto.user;

import org.programmers.springbootboardjpa.domain.user.Age;
import org.programmers.springbootboardjpa.domain.user.Name;
import org.programmers.springbootboardjpa.domain.user.Password;
import org.programmers.springbootboardjpa.domain.user.User;

import java.time.LocalDate;
import java.util.List;

public record UserCreateFormV1(String nickname, String password, String firstname, String lastname, LocalDate birthDate, List<String> interests) {
    public User convertToUser() {
        //TODO: userInterest 처리
        return (interests != null) ? new User(nickname, new Password(password), new Name(firstname, lastname), new Age(birthDate), null)
                : new User(nickname, new Password(password), new Name(firstname, lastname), new Age(birthDate));
    }
    }