package kr.co.boardmission.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_boards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Column(name = "content", length = 65335, nullable = false)
    private String content;

    //TODO : final을 명시하는 부분에 대해 어떻게 생각하나요? -> 현업에서는 final을 강제하나요? 근거가 있을까요?
    //TODO : 코틀린 컨벤션에 대해 어떻게 생각하나요?
    private Board(
            final String title,
            final String content
    ) {
        this.title = title;
        this.content = content;
    }

    public static Board create(
            final String title,
            final String content
    ) {
        return new Board(title, content);
    }
}
