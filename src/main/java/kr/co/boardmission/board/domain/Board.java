package kr.co.boardmission.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    private Board(
            final String title,
            final String content,
            final String createdBy
    ) {
        super(createdBy, null);
        this.title = title;
        this.content = content;
    }

    public static Board create(
            final String title,
            final String content,
            final String createdBy
    ) {
        return new Board(title, content, createdBy);
    }
}
