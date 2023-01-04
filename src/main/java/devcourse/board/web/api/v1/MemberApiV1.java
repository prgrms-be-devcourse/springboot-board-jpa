package devcourse.board.web.api.v1;

import devcourse.board.domain.member.MemberService;
import devcourse.board.domain.member.model.MemberJoinRequest;
import devcourse.board.domain.member.model.MemberResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static devcourse.board.web.authentication.AuthenticationUtil.getLoggedInMemberId;

@RestController
@RequestMapping("/api/v1/members")
public class MemberApiV1 {
    
    private final MemberService memberService;

    public MemberApiV1(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/my-page")
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
