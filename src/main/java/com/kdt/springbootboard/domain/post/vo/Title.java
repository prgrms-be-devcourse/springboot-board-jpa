package com.kdt.springbootboard.domain.post.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Title {

    @Column(name = "post_title")
    private String title;

    public Title(String title) {
        if(!validate(title)) throw new IllegalArgumentException("The number of Title characters exceeded the limit.");
        this.title = title;
    }

    public static boolean validate(String title) {
        return title.length() > 0 && title.length() <= 25;
    }

    public String getTitle() {
        return title;
    }
}
