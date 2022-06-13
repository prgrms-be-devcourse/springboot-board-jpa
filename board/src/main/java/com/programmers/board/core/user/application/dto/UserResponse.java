package com.programmers.board.core.user.application.dto;

import com.programmers.board.core.user.domain.Hobby;
import com.programmers.board.core.user.domain.User;

public class UserResponse {

    private Long id;

    private String name;

    private int age;

    private String hobby;

    protected UserResponse(){}

    public UserResponse(Long id, String name, int age, String hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static UserResponse of(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby().toString())
                .build();
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

    public String getHobby() {
        return hobby;
    }

    //Builder
    public static UserResponseBuilder builder(){
        return new UserResponseBuilder();
    }

    public static class UserResponseBuilder{

        private Long id;

        private String name;

        private int age;

        private String hobby;

        public UserResponseBuilder(){}

        public UserResponseBuilder id(Long id){
            this.id = id;
            return this;
        }

        public UserResponseBuilder name(String name){
            this.name = name;
            return this;
        }

        public UserResponseBuilder age(int age){
            this.age = age;
            return this;
        }

        public UserResponseBuilder hobby(String hobby){
            this.hobby = hobby;
            return this;
        }

        public UserResponse build(){
            return new UserResponse(this.id, this.name, this.age, this.hobby);
        }
    }
}
