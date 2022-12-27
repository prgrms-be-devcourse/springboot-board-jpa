package devcourse.board.domain.member.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private Integer age;

    private String hobby;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Member() {
    }

    public Member(String name) {
        this(name, null, null);
    }

    public Member(String name, Integer age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
