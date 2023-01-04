package devcourse.board.web.api.v2;


import devcourse.board.domain.member.MemberService;
import devcourse.board.domain.member.model.MemberJoinRequest;
import devcourse.board.domain.member.model.MemberResponse;
import devcourse.board.web.authentication.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v2/members")
public class MemberApiV2 {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final SessionManager sessionManager;

    private final MemberService memberService;

    public MemberApiV2(SessionManager sessionManager, MemberService memberService) {
        this.sessionManager = sessionManager;
        this.memberService = memberService;
    }

    @GetMapping("/my-page")
    public ResponseEntity<MemberResponse> getMember(
            HttpServletRequest request
    ) {
        Long loginMemberId = sessionManager.getLoginMemberIdentifier(request);
        return ResponseEntity.ok()
                .body(memberService.getMember(loginMemberId));
    }

    @PostMapping
    public ResponseEntity<Void> join(
            @Valid @RequestBody MemberJoinRequest joinRequest
    ) {
        memberService.join(joinRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
