package com.example.boardbackend.domain;

import com.example.boardbackend.dto.PostDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 45)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    /**
     * @ColumnDefault는 DDL에만 관여
     * default는 컬럼=null이 insert될때가 아닌 insert문에 해당 컬럼이 아예 빠져있을때 작동되는것
     * 따라서 @DynamicInsert로 view 컬럼이 null값일 경우 insert문에서 view 컬럼을 제외시켜버리면 default가 작동
     */
    @ColumnDefault("0")
    @Column(name = "view")
    private Long view;

    // 다대일 - 단방향 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, referencedColumnName = "id")
    private User user;

    // ---------------------------------------------------------------

    private Post(Long id, String title, String content, Long view, User user, LocalDateTime createdAt) {
        super(createdAt);
        this.id = id;
        this.title = title;
        this.content = content;
        this.view = view;
        this.user = user;
    }

    static public Post of(PostDto postDto){
        return new Post(
                postDto.getId(),
                postDto.getTitle(),
                postDto.getContent(),
                postDto.getView(),
                User.of(postDto.getUserDto()),
                postDto.getCreatedAt()
        );
    }

    // ---------------------------------------------------------------

    public void updatePost(String newTitle, String newContent) {
        this.title = newTitle;
        this.content = newContent;
    }

    public void updateView(Long newView){
        this.view = newView;
    }
}
