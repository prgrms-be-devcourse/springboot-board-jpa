package devcourse.board.domain.member.model;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
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
}
