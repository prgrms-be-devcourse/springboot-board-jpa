package com.example.springbootboard.domain.vo;

import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class Name {

    @Column(name = "name", nullable = false, length = 30)
    private String fullName;

    public Name(){}

    public Name(String fullName) {
        Assert.notNull(fullName, "name should not be null");
        Assert.isTrue(checkName(fullName), "Invalid name patterns(name must be korean and name length must be between 2 and 4 characters.)");
        this.fullName = fullName;
    }

    private static boolean checkName(String fullName) {
        return Pattern.matches("^[가-힣]{2,4}$", fullName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(fullName, name.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }

    @Override
    public String toString() {
        return "Name{" +
                "fullName='" + fullName + '\'' +
                '}';
    }

    public String getFullName() {
        return fullName;
    }
}