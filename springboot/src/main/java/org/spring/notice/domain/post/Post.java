package org.spring.notice.domain.post;

import lombok.*;
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
@ToString(of={"title", "content"})
@EqualsAndHashCode(of="id", callSuper = false)
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="writer_id", referencedColumnName = "id")
    private User writer;

    private Post(String title, String content, User writer){
        checkArgument(hasText(title), "제목이 공란입니다");
        checkNotNull(writer, "작성자는 null 일 수 없습니다");

        this.title = title;
        this.content = content;
        this.writer = writer;

        this.writer.getPosts().add(this);
    }

    public static Post write(String title, String content, User writer){
        return new Post(title, content, writer);
    }

}
