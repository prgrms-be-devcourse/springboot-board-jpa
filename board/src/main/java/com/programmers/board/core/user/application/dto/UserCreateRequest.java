package com.programmers.board.core.user.application.dto;

import com.programmers.board.core.user.domain.Hobby;
import com.programmers.board.core.user.domain.User;

public class UserCreateRequest {

    private final String name;
    
    private final int age;

    private final String hobby;

    public UserCreateRequest(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    //Getter
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

    //toEntity
    public User toEntity(){
        return User.builder()
                .name(this.name)
                .age(this.age)
                .hobby(Hobby.valueOf(this.hobby))
                .build();
    }

    //Builder
    public static UserCreateRequestBuilder builder(){
        return new UserCreateRequestBuilder();
    }

    public static class UserCreateRequestBuilder{

        private String name;
        private int age;
        private String hobby;

        public UserCreateRequestBuilder(){}

        public UserCreateRequestBuilder name(String name){
            this.name = name;
            return this;
        }

        public UserCreateRequestBuilder age(int age){
            this.age = age;
            return this;
        }

        public UserCreateRequestBuilder hobby(String hobby){
            this.hobby = hobby;
            return this;
        }

        public UserCreateRequest build(){
            return new UserCreateRequest(this.name, this.age, this.hobby);
        }

    }

}
