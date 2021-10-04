package com.programmers.jpaboard.member.domain;

import com.programmers.jpaboard.DateEntity;
import com.programmers.jpaboard.board.domian.Board;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Member extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name", length = 20)
    private String name;

    private int age;

    @ElementCollection
    @CollectionTable(name = "hobby", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "hobby")
    private List<String> hobbies = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<Board> boards = new ArrayList<>();

    @Builder
    public Member(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.hobbies = new ArrayList<>(hobbies);
    }

    public void addBoard(Board board) {
        board.setMember(this);
    }

    public void changeHobbies(List<String> hobbyList) {
        this.hobbies = new ArrayList<>(hobbyList);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public List<Board> getBoards() {
        return boards;
    }
}
