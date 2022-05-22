package com.prgrms.boardapp.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDto {
    private Long id;
    private String name;
    private Integer age;
    private String hobby;
    private List<PostDto> postDtoList;

    public UserDto(Long id, String name, Integer age, String hobby, List<PostDto> postDtoList) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.postDtoList = (postDtoList == null) ? new ArrayList<>() : postDtoList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public List<PostDto> getPostDtoList() {
        return postDtoList;
    }

    public void setPostDtoList(List<PostDto> postDtoList) {
        this.postDtoList = postDtoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static UserDtoBuilder builder() {
        return new UserDtoBuilder();
    }

    public static class UserDtoBuilder {
        private Long id;
        private String name;
        private Integer age;
        private String hobby;
        private List<PostDto> postDtoList;

        UserDtoBuilder() {
        }

        public UserDtoBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public UserDtoBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public UserDtoBuilder age(final Integer age) {
            this.age = age;
            return this;
        }

        public UserDtoBuilder hobby(final String hobby) {
            this.hobby = hobby;
            return this;
        }

        public UserDtoBuilder postDtoList(final List<PostDto> postDtoList) {
            this.postDtoList = postDtoList;
            return this;
        }

        public UserDto build() {
            return new UserDto(this.id, this.name, this.age, this.hobby, postDtoList);
        }
    }
}
