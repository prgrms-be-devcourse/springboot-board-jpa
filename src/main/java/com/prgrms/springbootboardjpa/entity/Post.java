package com.prgrms.springbootboardjpa.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post")
@ToString
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ_GEN")
    private Long id;

    private String title;

    @Lob
    private String content;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User author;


    public void setAuthor(User user) {
        if (this.author != null) {
            this.author.getPosts().remove(this);
        }
        this.author = user;
        user.getPosts().add(this);
    }

    public void changeContent(String content) {
        this.author.getPosts().remove(this);
        this.content = content;
        this.author.addPost(this);
    }
}
