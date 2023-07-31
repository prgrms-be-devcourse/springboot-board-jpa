package kr.co.boardmission.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.co.boardmission.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tbl_boards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseBoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Column(name = "content", length = 65335, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Board(
            final String title,
            final String content,
            final Member member
    ) {
        super(member.getName(), null);
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public static Board create(
            final String title,
            final String content,
            final Member member
    ) {
        return new Board(title, content, member);
    }
}
