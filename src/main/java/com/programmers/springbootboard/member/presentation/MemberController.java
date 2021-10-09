package com.programmers.springbootboard.member.presentation;

import com.programmers.springbootboard.common.dto.ResponseDto;
import com.programmers.springbootboard.common.dto.ResponseMessage;
import com.programmers.springbootboard.exception.error.DuplicationArgumentException;
import com.programmers.springbootboard.exception.ErrorMessage;
import com.programmers.springbootboard.exception.error.NotFoundException;
import com.programmers.springbootboard.member.application.MemberService;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.dto.MemberDetailResponse;
import com.programmers.springbootboard.member.dto.MemberEmailRequest;
import com.programmers.springbootboard.member.dto.MemberSignRequest;
import com.programmers.springbootboard.member.dto.MemberUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member")
    public ResponseEntity<ResponseDto> insertMember(@RequestBody MemberSignRequest request) {
        Email email = new Email(request.getEmail());
        if (memberService.existsByEmail(email)) {
            throw new DuplicationArgumentException(ErrorMessage.DUPLICATION_MEMBER_EMAIL);
        }
        memberService.insert(request);
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_SIGN_SUCCESS));
    }

    @DeleteMapping("/member")
    public ResponseEntity<ResponseDto> deleteMember(@RequestBody MemberEmailRequest request) {
        Email email = new Email(request.getEmail());
        if (memberService.existsByEmail(email)) {
            throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
        }
        memberService.deleteByEmail(email);
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_DELETE_SUCCESS));
    }

    @PatchMapping("/member")
    public ResponseEntity<ResponseDto> updateMember(@RequestBody MemberUpdateRequest request) {
        memberService.update(request);
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_UPDATE_SUCCESS));
    }

    @GetMapping("/member")
    public ResponseEntity<ResponseDto> member(@RequestBody MemberEmailRequest request) {
        Email email = new Email(request.getEmail());
        MemberDetailResponse member = memberService.findByEmail(email);
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_INQUIRY_SUCCESS, member));
    }

    @GetMapping("/members")
    public ResponseEntity<ResponseDto> members() {
        List<MemberDetailResponse> members = memberService.findAll();
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_INQUIRY_SUCCESS, members));
    }
}
