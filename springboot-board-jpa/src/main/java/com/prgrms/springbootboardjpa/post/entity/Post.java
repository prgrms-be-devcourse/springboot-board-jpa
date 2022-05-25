package com.prgrms.springbootboardjpa.post.entity;

import com.prgrms.springbootboardjpa.BaseEntity;
import com.prgrms.springbootboardjpa.post.dto.PostDto;
import com.prgrms.springbootboardjpa.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length=200)
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    //기본 EAGER
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public void setUser(User user){
        if (this.user != null)
            user.getPosts().remove(this);
        this.user = user;
        user.getPosts().add(this);
    }

    public void updatePost(PostDto postDto){
        if (postDto.getTitle() != null)
            this.title = postDto.getTitle();

        if (postDto.getContent() != null)
            this.content = postDto.getContent();
    }

}
