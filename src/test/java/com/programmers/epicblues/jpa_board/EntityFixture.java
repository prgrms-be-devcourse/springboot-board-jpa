package com.programmers.epicblues.jpa_board;

import com.programmers.epicblues.jpa_board.entity.Post;
import com.programmers.epicblues.jpa_board.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityFixture {

  public static Post getFirstPost() {
    return Post.builder().content("내용2").title("제목2")
        .createdBy("민수")
        .build();
  }

  public static Post getSecondPost() {
    return Post.builder().content("내용1").title("제목1")
        .createdBy("민성")
        .build();
  }

  public static User getUser() {
    return User.builder().name("민성").age(30).hobby("컵라면").createdBy("운영자").build();
  }

}
