package com.blackdog.springbootBoardJpa.domain.user.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;

@Embeddable
public class Age {

    @Column(nullable = false)
    @Positive
    private int ageValue;

    protected Age() {
    }

    public Age(final int ageValue) {
        this.ageValue = ageValue;
    }

    public int getAgeValue() {
        return ageValue;
    }

}
