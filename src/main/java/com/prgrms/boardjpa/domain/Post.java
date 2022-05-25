package com.prgrms.boardjpa.domain;

import com.prgrms.boardjpa.exception.WrongFormatException;
import com.prgrms.boardjpa.post.dto.PostResDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.regex.Pattern;

@Entity
@Getter
@Table(name = "post")
public class Post extends BaseEntity{

    private static final String TITLE_REGEX = "([\\w가-힣\\.\\(\\)\\[\\]\\-\"'?/*&^%$#@!~=+])+(( )*([\\w가-힣\\.\\(\\)\\[\\]\\-\"'?/*&^%$#@!~=+,])*)*";
    private static final String CONTENT_REGEX = "([\\w가-힣\\.\\(\\)\\[\\]\\-\"'?/*&^%$#@!~=+])+(( )*([\\w가-힣\\.\\(\\)\\[\\]\\-\"'?/*&^%$#@!~=+,])*)*";

    private static boolean validateTitle(String title){
        if(title.length() > 50) return false;
        return Pattern.matches(TITLE_REGEX, title);
    }

    private static boolean validateContent(String content){
        return Pattern.matches(CONTENT_REGEX, content);
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "author", referencedColumnName = "user_id")
    private User author;

    public void setAuthor(User user){
        if(Objects.nonNull(this.author)){
            this.author.getPosts().remove(this);
        }
        this.author = user;
        this.setCreatedBy(user.getId().toString());
        user.getPosts().add(this);
    }

    public void setTitle(String title) {
        if(!validateTitle(title)) throw new WrongFormatException("올바르지 않은 제목입니다.");
        this.title = title;
    }

    public void setContent(String content) {
        if(!validateContent(content)) throw new WrongFormatException("올바르지 않은 본문입니다.");
        this.content = content;
    }

    public PostResDto toResDto(){
        return PostResDto.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .author(this.author.toAuthorDto())
                .createdAt(super.getCreatedAt())
                .build();
    }

}
