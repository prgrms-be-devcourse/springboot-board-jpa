package com.prgrms.boardjpa.domain;

import com.prgrms.boardjpa.exception.LengthErrorException;
import com.prgrms.boardjpa.exception.WrongFormatException;
import com.prgrms.boardjpa.post.dto.PostResDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.regex.Pattern;

@Entity
@Getter
@Table(name = "post")
public class Post extends BaseEntity {

    public static final int MAX_TITLE_LENGTH = 50;
    public static final String TITLE_REGEX = "(( )*([\\w가-힣\\.\\(\\)\\[\\]\\-\"'?/*&^%$#@!~=+,])*)*";
    public static final String CONTENT_REGEX = "(( )*([\\w가-힣\\.\\(\\)\\[\\]\\-\"'?/*&^%$#@!~=+,])*)*";

    private static void validateTitle(String title) {
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new LengthErrorException("게시글 제목은 %d자를 넘을 수 없습니다.".formatted(MAX_TITLE_LENGTH));
        }
        if (!Pattern.matches(TITLE_REGEX, title)) {
            throw new WrongFormatException("올바르지 않은 게시글 제목입니다.");
        }
    }

    private static void validateContent(String content) {
        if (!Pattern.matches(CONTENT_REGEX, content)) {
            throw new WrongFormatException("올바르지 않은 게시글 본문입니다.");
        }
    }

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", referencedColumnName = "user_id")
    private User author;

    protected Post() {}

    public Post(String title, String content, User author) {
        validateTitle(title);
        validateContent(content);

        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void setAuthor(User user) {
        if (Objects.nonNull(this.author)) {
            this.author.getPosts().remove(this);
        }
        this.author = user;
        this.setCreatedBy(user.getId().toString());
        user.getPosts().add(this);
    }

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public void setContent(String content) {
        validateContent(content);
        this.content = content;
    }

    public PostResDto toResDto() {
        return PostResDto.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .author(this.author.toAuthorDto())
                .createdAt(super.getCreatedAt())
                .build();
    }

}
