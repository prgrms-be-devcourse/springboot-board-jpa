package com.programmers.board.core.user.domain;

import com.programmers.board.core.common.entity.BaseEntity;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "age")
    private int age;

    @Enumerated(EnumType.STRING)
    private Hobby hobby;

    protected User(){}

    public User(String name, int age, Hobby hobby) {
        validateName(name);
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

    // Valdiate logic
    private void validateName(String name){
        Assert.notNull(name, "이름을 등록해주세요.");
        Assert.isTrue(name.length() <= 20 && name.length() > 0, "이름의 길이는 20자 이하(필수)입니다.");
    }

}
