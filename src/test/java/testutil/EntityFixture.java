package testutil;

import com.programmers.epicblues.jpa_board.entity.Post;
import com.programmers.epicblues.jpa_board.entity.User;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityFixture {

  public static Post getFirstPost() {
    return Post.builder().content("내용2").title("제목2").build();
  }

  public static Post getSecondPost() {
    return Post.builder().content("내용1").title("제목1").build();
  }

  public static List<Post> getPostList() {
    return getPostList(10);
  }

  public static List<Post> getPostList(int size) {
    AtomicInteger integer = new AtomicInteger(1);
    return Stream.generate(integer::incrementAndGet).limit(size)
        .map(i -> Post.builder()
            .title(String.valueOf(i))
            .content(String.valueOf(i))
            .build())
        .collect(Collectors.toList());
  }

  public static User getUser() {
    return User.builder().name("민성").age(30).hobby("컵라면").createdBy("운영자").build();
  }

}
