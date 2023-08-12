package com.example.jpaboard.member.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Name {

    private String value;

    protected Name() {
    }

    public Name(String value) {
        validateName(value);
        this.value = value;
    }

    public void changeName(String value) {
        validateName(value);
        this.value = value;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException();
        }
    }

    public String getValue() {
        return value;
    }

}
