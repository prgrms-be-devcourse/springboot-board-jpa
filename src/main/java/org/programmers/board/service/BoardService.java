package org.programmers.board.service;

import org.programmers.board.dto.BoardUpdateRequest;
import org.programmers.board.entity.Board;
import org.programmers.board.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Long createBoard(Board board) {
        Board savedBoard = boardRepository.save(board);
        return savedBoard.getId();
    }

    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("조회할 수 없는 글입니다."));
    }

    public Page<Board> getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional
    public Long updateBoard(Long boardId, BoardUpdateRequest boardUpdateRequest) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException("조회할 수 없는 글입니다."));

        board.editBoard(boardUpdateRequest.getTitle(), boardUpdateRequest.getContent());

        return board.getId();
    }
}