package com.prgrms.board.domain;

import lombok.*;
import lombok.Builder.Default;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 10, unique = true)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @NotBlank
    private int age;

    private String hobby;

    @Default
    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    protected Member() {
    }
}
