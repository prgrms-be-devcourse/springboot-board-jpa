package yjh.jpa.springnoticeboard.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "post")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20)")
    private Long id;

    @Column(name = "title", columnDefinition = "varchar(100)", length = 30)
    private String title;

    @Column(name = "content", columnDefinition = "varchar(500)")
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public void setUser(User user) {
        if(Objects.nonNull(this.user)){
            this.user.getPosts().remove(this);
        }
        this.user = user;
        this.user.getPosts().add(this);
    }

}
