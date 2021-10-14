package domain;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 30, unique = true)
    private String name;

    @Column(name = "age")
    private int age;

    @Embedded
    private Hobby hobby;

    protected User() {
    }

    private User(String name, int age, Hobby hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static User createUser(String name, int age, Hobby hobby) {
        return new User(name, age, hobby);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Hobby getHobby() {
        return hobby;
    }
}
