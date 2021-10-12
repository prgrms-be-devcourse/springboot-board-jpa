package com.prgrms.dlfdyd96.board.post.service;

import com.prgrms.dlfdyd96.board.post.dto.PostResponse;
import com.prgrms.dlfdyd96.board.post.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class PostServiceTest {

  @Autowired
  private PostRepository postRepository;

  /*
  - [ ] PostDto보단 PostCreateDto 로 분리하여 응집도 높이고 결합도 줄이기
  - [ ] PostCreateDto Not Null Validation
  - [ ] 만들어질때 User가 존재하는지 판단해야함
  - [ ]
  */
  @Test
  @DisplayName("entity를 save할 수 있다.")
  void testSave() {
    // GIVEN
    PostResponse postDto = PostResponse.builder().
        build();
    // WHEN

    // THEN

  }
}