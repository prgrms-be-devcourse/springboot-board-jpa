package kdt.prgms.springbootboard.domain;

import java.util.Objects;
import javax.persistence.*;

@Embeddable
public class Hobby {

    @Column(name = "hobby_name")
    private String name;

    @Column(name = "hobby_type")
    @Enumerated(EnumType.STRING)
    private HobbyType hobbyType;

    protected Hobby() {
    }

    private Hobby(String name, HobbyType hobbyType) {
        this.name = name;
        this.hobbyType = hobbyType;
    }

    public static Hobby createHobby(String name, HobbyType hobbyType) {
        return new Hobby(name, hobbyType);
    }

    public String getName() {
        return name;
    }

    public HobbyType getHobbyType() {
        return hobbyType;
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
