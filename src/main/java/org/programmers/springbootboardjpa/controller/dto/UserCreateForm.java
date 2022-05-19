package org.programmers.springbootboardjpa.controller.dto;

import org.programmers.springbootboardjpa.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record UserCreateForm(String nickname, String password, String firstname, String lastname, LocalDate birthDate, List<String> interests) {
    public User convertToUser() {
        //TODO: userInterest 처리
        return (interests != null) ? new User(nickname, new Password(password), new Name(firstname, lastname), new Age(birthDate), null)
                : new User(nickname, new Password(password), new Name(firstname, lastname), new Age(birthDate));
    }
    }