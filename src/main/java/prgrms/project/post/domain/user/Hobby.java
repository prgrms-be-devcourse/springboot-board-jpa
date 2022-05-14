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
    private Long id;

    private String hobby;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Hobby(String hobby) {
        this.hobby = hobby;
    }

    public void assignUser(User user) {
        this.user = user;
        user.getHobbies().add(this);
    }
}
