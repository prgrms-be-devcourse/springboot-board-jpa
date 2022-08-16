package com.devcourse.springjpaboard.post.controller.stub;

import com.devcourse.springjpaboard.application.post.controller.dto.CreatePostRequest;
import com.devcourse.springjpaboard.application.post.controller.dto.UpdatePostRequest;
import com.devcourse.springjpaboard.application.post.model.Post;
import com.devcourse.springjpaboard.application.post.service.dto.PostResponse;
import com.devcourse.springjpaboard.application.user.model.User;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class PostStubs {

  private PostStubs() {
  }

  public static CreatePostRequest createPostRequest() {
    return new CreatePostRequest("test-title", "test-content", 1L);
  }

  public static UpdatePostRequest updatePostRequest() {
    return new UpdatePostRequest("update-title", "update-content");
  }

  public static PostResponse createPostResponse() {
    return new PostResponse("test-title", "test-content");
  }

  public static PostResponse updatePostResponse() {
    return new PostResponse("update-title", "update-content");
  }

  public static Page<PostResponse> pagePostResponse() {
    return new PageImpl<>(
        List.of(
            new PostResponse("test-title1", "test-content1"),
            new PostResponse("test-title2", "test-content2"),
            new PostResponse("test-title3", "test-content3"),
            new PostResponse("test-title4", "test-content4"),
            new PostResponse("test-title5", "test-content5")
        )
    );
  }

  public static Post post(User user) {
     return new Post(
        "test-title",
        "test-content",
        user
    );
  }

  public static Stream<Arguments> blankTitlePostRequest() {
    return Stream.of(
        Arguments.of("", "test-content", 1L),
        Arguments.of(" ", "test-content", 1L),
        Arguments.of(null, "test-content", 1L)
    );
  }

  public static Stream<Arguments> blankContentPostRequest() {
    return Stream.of(
        Arguments.of("test-title", "", 1L),
        Arguments.of("test-title", " ", 1L),
        Arguments.of("test-title", null, 1L)
    );
  }

  public static Stream<Arguments> notValidUserIdPostRequest() {
    return Stream.of(
        Arguments.of("test-title", "test-content", 0L),
        Arguments.of("test-title", "test-content", -1L),
        Arguments.of("test-title", "test-content", null)
    );
  }
}
