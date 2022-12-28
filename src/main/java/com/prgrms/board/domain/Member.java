package com.prgrms.board.domain;

import lombok.*;
import lombok.Builder.Default;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
@EqualsAndHashCode(of = {"id", "name"}, callSuper = false)
public class Member extends TimeBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 10, unique = true)
    @NotBlank(message = "{exception.member.name.null}")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "{exception.member.age.null}")
    @Positive(message = "{exception.member.age.positive}")
    private int age;

    private String hobby;

    @Default
    @OneToMany(mappedBy = "writer", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

}
