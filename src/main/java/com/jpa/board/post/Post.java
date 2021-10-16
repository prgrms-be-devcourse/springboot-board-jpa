package com.jpa.board.post;

import com.jpa.board.common.BaseEntity;
import com.jpa.board.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Post extends BaseEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;

    @Builder
    public Post(String title, String content, User user){
        this.title = title;
        this.content = content;
        this.user = user;
        addCreatedBy(user.getName());
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

}
