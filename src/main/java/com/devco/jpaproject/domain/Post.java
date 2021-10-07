package com.devco.jpaproject.domain;

import com.devco.jpaproject.domain.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@EqualsAndHashCode
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    public void updateTitle(String title){
        this.title = title;
    }

    public void updateContent(String content){
        this.content = content;
    }

    /**
     * 연관관계 편의 매소드
     */
    public void setWriter(User writer){
        if(Objects.nonNull(this.writer)){
            this.writer.getPosts().remove(this);
        }

        this.writer = writer;        //양방향
        writer.getPosts().add(this); //연결을 함
    }

    public void delete(){
        if(Objects.nonNull(this.writer)){
            this.writer.getPosts().remove(this);
        }
    }


}
