package kdt.prgms.springbootboard.domain;

import javax.persistence.*;

@Embeddable
public class Hobby {

    @Column(name = "hobby_name", nullable = false)
    private String name;

    @Column(name = "hobby_type", nullable = false)
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
}
