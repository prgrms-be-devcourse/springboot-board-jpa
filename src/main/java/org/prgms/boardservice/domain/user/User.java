package org.prgms.boardservice.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import org.prgms.boardservice.domain.BaseTime;
import org.prgms.boardservice.domain.post.Post;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 20, unique = true)
    @NotBlank
    private String email;

    @Column(length = 20)
    @NotBlank
    private String password;

    @Column(length = 10, unique = true)
    @NotBlank
    private String nickname;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Post> posts = new ArrayList<>();
}
