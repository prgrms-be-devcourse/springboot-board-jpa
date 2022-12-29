package com.prgrms.board.domain;

import lombok.*;
import lombok.Builder.Default;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.prgrms.board.util.MemberValidator.validateMemberAge;
import static com.prgrms.board.util.MemberValidator.validateMemberName;

@Entity
@Getter
@Table(name = "members")
@EqualsAndHashCode(of = {"id", "name"}, callSuper = false)
public class Member extends TimeBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 10, unique = true)
    private String name;

    @Column(nullable = false)
    private int age;

    private String hobby;

    @Default
    @OneToMany(mappedBy = "writer", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();


    protected Member() {
    }

    @Builder
    private Member(Long id, String name, int age, String hobby, List<Post> posts) {
        this.id = id;

        validateMemberName(name);
        this.name = name;

        validateMemberAge(age);
        this.age = age;

        this.hobby = hobby;
        this.posts = posts;
    }
}
