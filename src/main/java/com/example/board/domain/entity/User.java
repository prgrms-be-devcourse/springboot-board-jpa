package com.example.board.domain.entity;


import com.example.board.domain.BaseTime;
import com.example.board.dto.user.UserResponseDto;
import com.example.board.dto.user.UserUpdateRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Long id;

    @Column(name = "usr_name", nullable = false)
    private String name;

    @Column(name = "usr_email", nullable = false)
    private String email;

    @Column(name = "usr_password", nullable = false)
    private String password;

    @Column(name = "usr_age")
    private int age;

    @Column(name = "usr_hobby")
    private String hobby;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Like> likes = new ArrayList<>();

    //업데이트 dirty checking
    public void update(UserUpdateRequestDto requestDto) {
        this.name = (requestDto.getName() != null) ? requestDto.getName() : this.name;
        this.email = (requestDto.getEmail() != null) ? requestDto.getEmail() : this.email;
        this.password = (requestDto.getPassword() != null) ? requestDto.getPassword() : this.password;
        this.age = (requestDto.getAge() != null) ? requestDto.getAge() : this.age;
        this.hobby = (requestDto.getHobby() != null) ? requestDto.getHobby() : this.hobby;
    }

    //Dto 변환 로직
    public UserResponseDto from() {
        return UserResponseDto.builder()
                .name(name)
                .email(email)
                .age(age)
                .hobby(hobby)
                .build();
    }
}
