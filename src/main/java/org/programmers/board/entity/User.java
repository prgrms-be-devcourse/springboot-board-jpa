package org.programmers.board.entity;

import org.programmers.board.entity.vo.UserName;

import javax.persistence.*;
import java.time.LocalDate;
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
    private UserName userName;

    private int age;

    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();

    protected User() {

    }

    public User(UserName userName, int age, String hobby) {
        super(LocalDate.now());
        this.userName = userName;
        this.age = age;
        this.hobby = hobby;
    }

    public void addBoard(Board board) {
        board.setUser(this);
    }

    public Long getId() {
        return id;
    }

    public UserName getUserName() {
        return userName;
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