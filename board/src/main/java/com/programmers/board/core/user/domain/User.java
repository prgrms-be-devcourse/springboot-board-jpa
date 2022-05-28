package com.programmers.board.core.user.domain;

import com.programmers.board.core.common.entity.BaseEntity;
import com.programmers.board.core.post.domain.Post;
import lombok.AccessLevel;
import lombok.UserBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.UserBuilder.EqualsUserBuilder;
import org.apache.commons.lang3.UserBuilder.HashCodeUserBuilder;
import org.apache.commons.lang3.UserBuilder.ToStringUserBuilder;
import org.apache.commons.lang3.UserBuilder.ToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "age")
    private int age;

    @Enumerated(EnumType.STRING)
    private Hobby hobby;

    protected User(){}

    public User(String name, int age, Hobby hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    //Getter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Hobby getHobby() {
        return hobby;
    }

    //Update

    public void updateAge(int age){
        this.age = age;
    }

    public void updateHobby(Hobby hobby){
        this.hobby = hobby;
    }

    //UserBuilder
    public static UserBuilder builder(){
        return new UserBuilder();
    }
    public static class UserBuilder{

        private String name;

        private int age;

        private Hobby hobby;

        public UserBuilder(){}

        public UserBuilder name(String name){
            this.name = name;
            return this;
        }

        public UserBuilder age(int age){
            this.age = age;
            return this;
        }

        public UserBuilder hobby(Hobby hobby){
            this.hobby = hobby;
            return this;
        }

        public User build(){
            return new User(this.name, this.age, this.hobby);
        }

    }

}
