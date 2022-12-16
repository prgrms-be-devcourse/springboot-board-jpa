package com.prgrms.boardjpa.domain.member;

import com.prgrms.boardjpa.domain.BaseEntity;
import com.prgrms.boardjpa.domain.post.Post;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 20)
    private String name;
    private int age;
    @Enumerated
    private Hobby hobby;

    @OneToMany(mappedBy = "member")
    private List<Post> posts;

    public void changeName(String name) {
        this.name = name;
    }
}
