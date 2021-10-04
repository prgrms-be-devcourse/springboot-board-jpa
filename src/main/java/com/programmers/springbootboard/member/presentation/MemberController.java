package com.programmers.springbootboard.member.presentation;

import com.programmers.springbootboard.common.dto.ResponseDto;
import com.programmers.springbootboard.common.dto.ResponseMessage;
import com.programmers.springbootboard.exception.DuplicationArgumentException;
import com.programmers.springbootboard.exception.ErrorMessage;
import com.programmers.springbootboard.member.application.MemberService;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.dto.MemberDetailResponse;
import com.programmers.springbootboard.member.dto.MemberSignRequest;
import com.programmers.springbootboard.member.dto.MemberUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

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
    public ResponseEntity<ResponseDto> deleteMember(@RequestBody Email email) {
        memberService.delete(email);
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_DELETE_SUCCESS));
    }

    @PutMapping("/member")
    public ResponseEntity<ResponseDto> updateMember(@RequestBody MemberUpdateRequest request) {
        memberService.update(request);
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_UPDATE_SUCCESS));
    }

    @GetMapping("/member")
    public ResponseEntity<ResponseDto> member(@RequestBody Email email) {
        MemberDetailResponse member = memberService.member(email);
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_INQUIRY_SUCCESS, member));
    }

    @GetMapping("/members")
    public ResponseEntity<ResponseDto> members() {
        List<MemberDetailResponse> members = memberService.members();
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_INQUIRY_SUCCESS, members));
    }

}
