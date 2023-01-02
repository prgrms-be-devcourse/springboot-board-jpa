package devcourse.board.api.controller;

import devcourse.board.domain.member.MemberService;
import devcourse.board.domain.member.model.MemberJoinRequest;
import devcourse.board.domain.member.model.MemberResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static devcourse.board.api.authentication.AuthenticationUtil.getLoggedInMemberId;

@RestController
@RequestMapping("/members")
public class MemberApiController {

    private final MemberService memberService;

    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<MemberResponse> getMember(
            HttpServletRequest request
    ) {
        Long loggedInMemberId = getLoggedInMemberId(request);
        return ResponseEntity.ok()
                .body(memberService.getMember(loggedInMemberId));
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
