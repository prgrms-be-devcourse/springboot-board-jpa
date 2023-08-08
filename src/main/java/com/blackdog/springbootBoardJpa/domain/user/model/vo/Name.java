package com.blackdog.springbootBoardJpa.domain.user.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class Name {

    @NotBlank
    @Column(nullable = false)
    private String nameValue;

    protected Name() {
    }

    public Name(final String nameValue) {
        this.nameValue = nameValue;
    }

    public String getNameValue() {
        return nameValue;
    }

}
