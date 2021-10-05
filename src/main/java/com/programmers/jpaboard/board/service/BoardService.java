package com.programmers.jpaboard.board.service;

import com.programmers.jpaboard.board.domian.Board;
import com.programmers.jpaboard.board.repository.BoardRepository;
import com.programmers.jpaboard.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Board saveBoard(Board board, Member member) {
        board.setMember(member);
        return boardRepository.save(board);
    }

    @Transactional
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional
    public Board findOne(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException(""));
    }
}
