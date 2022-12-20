package org.programmers.board.entity;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
public class Boards {

    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    public void add(Board board) {
        boards.add(board);
    }

    public void remove(Board board) {
        boards.remove(board);
    }

    public List<Board> getAllBoard() {
        return Collections.unmodifiableList(boards);
    }
}