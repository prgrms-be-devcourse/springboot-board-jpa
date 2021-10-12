package com.prgrms.board.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name ="post")
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title", nullable = false, length = 250)
    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(){
    }

    @Builder
    private Post(User user, String title, String content){
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void changeInfo(String title, String content){
        this.title = title;
        this.content = content;
    }

}
