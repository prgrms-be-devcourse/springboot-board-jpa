package org.prgms.board.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.prgms.board.common.BaseTime;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseTime {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    private int age;
    private String hobby;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

    protected User() {
    }

    @Builder
    private User(Long id, String name, int age, String hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void changeInfo(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void removeUser() {
        this.deleted = true;
    }

}
