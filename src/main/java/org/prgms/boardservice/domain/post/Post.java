package org.prgms.boardservice.domain.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.prgms.boardservice.domain.BaseTime;
import org.prgms.boardservice.domain.user.User;

@Entity
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    @NotBlank
    private String title;

    @Lob
    @NotBlank
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
