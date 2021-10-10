package com.programmers.springbootboard.member.presentation;

import com.programmers.springbootboard.common.dto.ResponseDto;
import com.programmers.springbootboard.common.dto.ResponseMessage;
import com.programmers.springbootboard.exception.error.DuplicationArgumentException;
import com.programmers.springbootboard.exception.ErrorMessage;
import com.programmers.springbootboard.member.application.MemberService;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.dto.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api", produces = MediaTypes.HAL_JSON_VALUE)
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(MemberController.class);
    }

    @PostMapping(value = "/member", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> insertMember(@RequestBody MemberSignRequest request) {
        Email email = new Email(request.getEmail());
        if (memberService.existsByEmail(email)) {
            throw new DuplicationArgumentException(ErrorMessage.DUPLICATION_MEMBER_EMAIL);
        }
        MemberDetailResponse member = memberService.insert(request);

        EntityModel<MemberDetailResponse> data = EntityModel.of(
                member,
                getLinkToAddress().slash("member").withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash("member/" + member.getId()).withRel("delete").withType(HttpMethod.DELETE.name()),
                getLinkToAddress().slash("member/" + member.getId()).withRel("update").withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash("member/" + member.getId()).withRel("get").withType(HttpMethod.GET.name()),
                getLinkToAddress().slash("members").withRel("get-all").withType(HttpMethod.GET.name())
        );

        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_SIGN_SUCCESS, data));
    }

    @DeleteMapping(value = "/member/{id}")
    public ResponseEntity<ResponseDto> deleteMember(@PathVariable Long id) {
        MemberDeleteResponse member = memberService.deleteById(id);

        EntityModel<MemberDeleteResponse> data = EntityModel.of(member,
                getLinkToAddress().slash("member/" + member.getId()).withSelfRel().withType(HttpMethod.DELETE.name()),
                getLinkToAddress().slash("member").withRel("insert").withType(HttpMethod.PUT.name()),
                getLinkToAddress().slash("member/" + member.getId()).withRel("get").withType(HttpMethod.GET.name()),
                getLinkToAddress().slash("members").withRel("get-all").withType(HttpMethod.GET.name())
        );

        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_DELETE_SUCCESS, data));
    }

    @PatchMapping(value = "/member/{id}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> updateMember(@PathVariable Long id, @RequestBody MemberUpdateRequest request) {
        MemberDetailResponse member = memberService.update(id, request);

        EntityModel<MemberDetailResponse> data = EntityModel.of(member,
                getLinkToAddress().slash("member/" + member.getId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash("member/" + member.getId()).withRel("delete").withType(HttpMethod.DELETE.name()),
                getLinkToAddress().slash("member/" + member.getId()).withRel("get").withType(HttpMethod.GET.name()),
                getLinkToAddress().slash("members").withRel("get-all").withType(HttpMethod.GET.name()));

        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_UPDATE_SUCCESS, data));
    }

    @GetMapping(value = "/member/{id}")
    public ResponseEntity<ResponseDto> member(@PathVariable Long id) {
        MemberDetailResponse member = memberService.findById(id);

        EntityModel<MemberDetailResponse> data = EntityModel.of(member,
                getLinkToAddress().slash("member/" + member.getId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().slash("member/" + member.getId()).withRel("update").withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash("member/" + member.getId()).withRel("delete").withType(HttpMethod.DELETE.name()),
                getLinkToAddress().slash("members").withRel("get-all").withType(HttpMethod.GET.name()));

        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_INQUIRY_SUCCESS, data));
    }

    @GetMapping(value = "/members")
    public ResponseEntity<ResponseDto> members() {
        List<MemberDetailResponse> members = memberService.findAll();
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_INQUIRY_SUCCESS, members));
    }
}
