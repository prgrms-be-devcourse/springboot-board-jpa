package org.programmers.board.entity;

import org.programmers.board.entity.vo.Name;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Embedded
    private Name name;

    private int age;

    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    protected User() {

    }

    public User(Name name, int age, String hobby) {
        super(LocalDateTime.now());
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void addBoard(Board board) {
        board.setUser(this);
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

    public List<Board> getBoards() {
        return boards;
    }
}