package com.programmers.jpaboard.board.domian;

import com.programmers.jpaboard.DateEntity;
import com.programmers.jpaboard.board.domian.vo.Content;
import com.programmers.jpaboard.board.domian.vo.Title;
import com.programmers.jpaboard.member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Board extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Member member;

    @Builder
    public Board(String title, String content) {
        this.title = new Title(title);
        this.content = new Content(content);
    }

    public void setMember(Member member) {
        if (Objects.nonNull(this.member)) {
            member.getBoards().remove(this);
        }
        this.member = member;
        member.getBoards().add(this);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title.getTitle();
    }

    public String getContent() {
        return content.getContent();
    }

    public Member getMember() {
        return member;
    }
}
