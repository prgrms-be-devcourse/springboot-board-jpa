package yjh.jpa.springnoticeboard.domain.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public void setUser(User user) {
//        if(Objects.nonNull(this.user)){
////            this.user.getPosts().removeIf(e -> e.getId().equals(user.getId()));
//            log.info("getPosts 값 출력 {}", user.getPosts());
//            log.info("getPosts 값 출력 {}", user.getPosts().size());
//            this.user.getPosts().forEach(e -> {e.getId().equals(user.getId());
//                e.content=this.content;
//                e.title=this.title;
//                e.user=user;
//            });
//            return;
//        }
        this.user = user;
        this.user.getPosts().add(this);
    }

}
