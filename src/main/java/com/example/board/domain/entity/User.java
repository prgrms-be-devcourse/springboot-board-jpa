package com.example.board.domain.entity;


import com.example.board.domain.BaseTime;
import com.example.board.dto.user.UserResponseDto;
import com.example.board.dto.user.UserUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public User(Long id, String name, String email, String password, int age, String hobby) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.hobby = hobby;
    }

    //업데이트 dirty checking
    public void update(UserUpdateRequestDto requestDto) {
        //질문 : updateRequestDto에 validation으로 값을 검증하는 로직을 작성하면 null값이 들어오지 못해 동적으로 엔티티의 변경이 불가능합니다.
        //그렇다고 valdation으로 검증 로직을 제거 하면 원하지 않은 값이 들어와 엔티티에 문제가 생깁니다.
        //이전에는 생성자로 새로 생성하고 save 하는 머지 방식을 선택하여 업데이트를 하였을 때는 위와같은 문제가 생기지 않았으나
        //dirty chekcing으로 엔티티를 변경할 때는 이러한 문제를 어떻게 해결하는지 궁굼합니다.!!
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
