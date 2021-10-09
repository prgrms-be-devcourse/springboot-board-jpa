package com.homework.springbootboard.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "post")
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Please type title")
    private String title;

    @Lob
    @NotNull(message = "Please type content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    public void setUser(User user){
        if(Objects.nonNull(this.user)){
            this.user.getPostList().remove(this);
        }
        this.user = user;
        user.getPostList().add(this);
    }
}
