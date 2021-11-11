package kdt.prgms.springbootboard.domain;

import java.util.Objects;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Hobby {

    @Column(name = "hobby_name")
    private String name;

    @Column(name = "hobby_type")
    @Enumerated(EnumType.STRING)
    private HobbyType hobbyType;

    public Hobby(String name, HobbyType hobbyType) {
        this.name = name;
        this.hobbyType = hobbyType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hobby hobby = (Hobby) o;
        return Objects.equals(name, hobby.name) && hobbyType == hobby.hobbyType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, hobbyType);
    }
}
