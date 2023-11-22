package jehs.springbootboardjpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 59)
    private String name;

    @Column(name = "age")
    private Long age;

    @Column(name = "hobby", length = 59)
    private String hobby;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;
}
