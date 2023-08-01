package kr.co.boardmission.board.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.boardmission.BoardMissionApplication;
import kr.co.boardmission.board.domain.Board;
import kr.co.boardmission.board.domain.BoardFactory;
import kr.co.boardmission.board.domain.BoardRepository;
import kr.co.boardmission.board.dto.request.BoardRequest;
import kr.co.boardmission.member.application.MemberService;
import kr.co.boardmission.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest(classes = BoardMissionApplication.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardRepository boardRepository;

    private BoardRequest request;
    private Member member;
    private Board board;

    @BeforeEach
    void beforeEach() {
        member = memberService.createMember("testMemberName");
        request = BoardFactory.createBoardCreateRequest(member.getMemberId());
        board = boardRepository.save(BoardFactory.createBoard("title", "content", member));
    }

    @DisplayName("/api/v1/boards - 게시판 등록 API 테스트 with 성공")
    @Test
    void create_board_api_success() throws Exception {
        // When // Then
        mockMvc.perform(post("/api/v1/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andDo(document("boards/createBoard",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @DisplayName("/api/v1/boards/{board_id} - 게시판 단건 조회 API 테스트 with 성공")
    @Test
    void get_board_api_success() throws Exception {
        // When // Then
        mockMvc.perform(get("/api/v1/boards/" + board.getBoardId()))
                .andDo(print())
                .andDo(document("boards/getBoard",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.createdBy").exists());
    }

    @DisplayName("/api/v1/boards - 게시판 페이징 조회 API 테스트 with 성공")
    @Test
    void get_boards_paging_success() throws Exception {
        // Given
        Board board1 = BoardFactory.createBoard("t1", "c1", member);
        Board board2 = BoardFactory.createBoard("t2", "c2", member);
        Board board3 = BoardFactory.createBoard("t3", "c3", member);
        Board board4 = BoardFactory.createBoard("t4", "c4", member);
        List<Board> boards = new ArrayList<>(List.of(board, board1, board2, board3, board4));

        boardRepository.saveAll(boards);

        // When // Then
        mockMvc.perform(get("/api/v1/boards")
                        .param("page", "1")
                        .param("size", "2"))
                .andDo(print())
                .andDo(document("boards/getBoards",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.*", hasSize(2)));
    }

    @DisplayName("/api/v1/boards/{board_id} - 게시글 수정 API 테스트 with 성공")
    @Test
    void update_member_success() throws Exception {
        mockMvc.perform(put("/api/v1/boards/" + board.getBoardId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andDo(document("boards/updateBoard",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(content().string(member.getName()));
    }
}
