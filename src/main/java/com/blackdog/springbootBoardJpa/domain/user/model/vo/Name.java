package com.blackdog.springbootBoardJpa.domain.user.model.vo;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import org.springframework.util.Assert;

@Embeddable
public class Name {

    @NotBlank
    @Column(nullable = false)
    private String nameValue;

    protected Name() {
    }

    public Name(final String nameValue) {
        Assert.isTrue(StringUtils.isNotBlank(nameValue), "name must be given");
        this.nameValue = nameValue;
    }

    public String getNameValue() {
        return nameValue;
    }

}
