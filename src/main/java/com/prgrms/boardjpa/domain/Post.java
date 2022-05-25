package com.prgrms.boardjpa.domain;

import com.prgrms.boardjpa.post.dto.PostResDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "post")
public class Post extends BaseEntity{
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
