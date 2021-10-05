package com.programmers.jpaboard.board.service;

import com.programmers.jpaboard.board.domian.Board;
import com.programmers.jpaboard.board.repository.BoardRepository;
import com.programmers.jpaboard.member.domain.Member;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board saveBoard(Board board, Member member) {
        board.setMember(member);
        return boardRepository.save(board);
    }
}
