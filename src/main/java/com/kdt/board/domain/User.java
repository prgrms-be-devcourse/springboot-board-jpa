package com.kdt.board.domain;

import com.kdt.board.dto.post.PostResponse;
import com.kdt.board.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Builder
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    public User() {  }

    public String getName() {
        return this.name;
    }

    public User changeName(String name) {
        this.name = name;
        return this;
    }

    public Long getId() {
        return this.id;
    }
}
