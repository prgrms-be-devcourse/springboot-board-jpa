package devcourse.board.domain.member.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private Integer age;

    private String hobby;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Member() {
    }

    public static Member create(String email, String password, String name, Integer age, String hobby) {
        Member member = new Member();
        member.email = email;
        member.password = password;
        member.name = name;
        member.age = age;
        member.hobby = hobby;
        member.createdAt = LocalDateTime.now();

        return member;
    }

    public boolean matchId(Long id) {
        return this.id.equals(id);
    }

    public boolean matchEmail(String email) {
        return this.email.equals(email);
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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
