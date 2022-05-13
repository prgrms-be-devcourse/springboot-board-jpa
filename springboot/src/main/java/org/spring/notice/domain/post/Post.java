package org.spring.notice.domain.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spring.notice.domain.BaseEntity;
import org.spring.notice.domain.user.User;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.springframework.util.StringUtils.hasText;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="post")
public class Post extends BaseEntity {

    /* 게시글 아이디 */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /* 게시글 제목 */
    @Column(nullable = false)
    private String title;

    /* 게시글 내용 */
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    private Post(String title, String content, User writer){
        checkArgument(hasText(title), "제목이 공란입니다");
        checkNotNull(writer, "작성자는 null 일 수 없습니다");

        this.title = title;
        this.content = content;
        this.user = writer;

        this.user.getPosts().add(this);
    }

    public static Post write(String title, String content, User writer){
        return new Post(title, content, writer);
    }

}
