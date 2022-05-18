package prgrms.project.post.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Hobby {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "hobby_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 30)
    private String hobby;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Hobby(String hobby) {
        this.hobby = hobby;
    }

    public void assignUser(User user) {
        this.user = user;
        user.getHobbies().add(this);
    }
}
