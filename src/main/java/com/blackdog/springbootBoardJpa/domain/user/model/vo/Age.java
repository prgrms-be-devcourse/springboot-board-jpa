package com.blackdog.springbootBoardJpa.domain.user.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;
import org.springframework.util.Assert;

@Embeddable
public class Age {

    @Column(nullable = false)
    @Positive
    private int ageValue;

    protected Age() {
    }

    public Age(final int ageValue) {
        Assert.isTrue(ageValue > 0, "나이는 1살 이상이어야 합니다.");
        this.ageValue = ageValue;
    }

    public int getAgeValue() {
        return ageValue;
    }

}
