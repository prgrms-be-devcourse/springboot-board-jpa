package jehs.springbootboardjpa.entity;

import jakarta.persistence.*;
import jehs.springbootboardjpa.entity.vo.Age;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 59)
    private String name;

    @Embedded
    private Age age;

    @Column(name = "hobby", length = 59)
    private String hobby;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts;

    public boolean isSameName(String name) {
        return this.getName().equals(name);
    }
}
