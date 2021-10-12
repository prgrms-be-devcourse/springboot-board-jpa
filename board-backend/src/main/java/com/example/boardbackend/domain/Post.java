package com.example.boardbackend.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Objects;

@Getter @Setter
@DynamicInsert
@Entity
@Table(name = "post")
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 45)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    // @ColumnDefault는 DDL에만 관여
    // default는 컬럼=null이 insert될때가 아닌 insert문에 해당 컬럼이 아예 빠져있을때 작동되는것
    // 따라서 @DynamicInsert로 view 컬럼이 null값일 경우 insert문에서 view 컬럼을 제외시켜버리면 default가 작동
    @ColumnDefault("0")
    @Column(name = "view")
    private Long view;

    // 다대일 - 단방향 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, referencedColumnName = "id")
    private User user;


    // 연관관계 편의 메소드
//    public void setUser(User user){
//        if (Objects.nonNull(this.user)) {
//            this.user.getPosts().remove(this);
//        }
//        this.user = user;
//        user.getPosts().add(this);
//    }

}
