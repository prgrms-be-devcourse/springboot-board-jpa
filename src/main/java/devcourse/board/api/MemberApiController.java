package devcourse.board.api;

import devcourse.board.domain.member.MemberService;
import devcourse.board.domain.member.model.MemberRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberApiController {

    private final MemberService memberService;

    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Void> join(
            @RequestBody MemberRequest.JoinDto joinDto
    ) {
        memberService.join(memberJoinDto);
        return ResponseEntity.ok()
                .build();
    }
}
