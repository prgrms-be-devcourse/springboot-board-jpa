package com.programmers.jpaboard.board.domian;

import com.programmers.jpaboard.DateEntity;
import com.programmers.jpaboard.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "board")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Board extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    private Long id;

    @Column(length = 50)
    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Member member;

    @Builder
    public Board(String title, String content) {
        this.title = title;
        this.content = content;
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
        return title;
    }

    public String getContent() {
        return content;
    }

    public Member getMember() {
        return member;
    }
}
