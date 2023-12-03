package com.programmers.springbootboardjpa.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test Post Entity")
class PostTest {

    private static User user;

    @BeforeEach
    void init() {
        // Arrange
        String expectedName = "이름";
        int expectedAge = 26;
        String expectedHobby = "취미";
        // Act
        user = User.builder()
                .name(expectedName)
                .age(expectedAge)
                .hobby(expectedHobby)
                .build();
    }

    @Test
    void testCreatePost(){
        // Arrange
        String expectedTitle = "제목";
        String expectedContent = "내용";
        // Act
        Post actualResult = Post.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .user(user)
                .build();
        // Assert
        assertThat(actualResult.getTitle()).isEqualTo(expectedTitle);
        assertThat(actualResult.getContent()).isEqualTo(expectedContent);
        assertThat(actualResult.getCreatedBy()).isEqualTo(user.getName());
        assertThat(user.getPostList()).contains(actualResult);
    }

}