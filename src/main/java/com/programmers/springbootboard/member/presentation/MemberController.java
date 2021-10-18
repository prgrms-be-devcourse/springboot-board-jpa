package com.programmers.springbootboard.member.presentation;

import com.programmers.springbootboard.common.converter.ResponseConverter;
import com.programmers.springbootboard.common.dto.ResponseDto;
import com.programmers.springbootboard.common.dto.ResponseMessage;
import com.programmers.springbootboard.member.application.MemberService;
import com.programmers.springbootboard.member.converter.MemberConverter;
import com.programmers.springbootboard.member.dto.request.MemberUpdateRequest;
import com.programmers.springbootboard.member.dto.response.MemberSignResponse;
import com.programmers.springbootboard.member.dto.response.MemberUpdateResponse;
import com.programmers.springbootboard.member.dto.bundle.MemberDeleteBundle;
import com.programmers.springbootboard.member.dto.bundle.MemberFindBundle;
import com.programmers.springbootboard.member.dto.bundle.MemberSignBundle;
import com.programmers.springbootboard.member.dto.response.MemberDeleteResponse;
import com.programmers.springbootboard.member.dto.request.MemberSignRequest;
import com.programmers.springbootboard.member.dto.response.MemberDetailResponse;
import com.programmers.springbootboard.member.dto.bundle.MemberUpdateBundle;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/member", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberConverter memberConverter;
    private final ResponseConverter responseConverter;

    private static final String INSERT_METHOD = "insert";
    private static final String DELETE_METHOD = "delete";
    private static final String UPDATE_METHOD = "update";
    private static final String GET_METHOD = "get";
    private static final String GET_ALL_METHOD = "get-all";

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(MemberController.class);
    }

    @PostMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> sign(@RequestBody MemberSignRequest request) {
        MemberSignBundle bundle = memberConverter.toMemberSignBundle(request);
        MemberSignResponse response = memberService.insert(bundle);

        // TODO
        EntityModel<MemberSignResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getMemberId()).withRel(DELETE_METHOD).withType(HttpMethod.DELETE.name()),
                getLinkToAddress().slash(response.getMemberId()).withRel(UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getMemberId()).withRel(GET_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(GET_ALL_METHOD).withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                HttpStatus.CREATED,
                ResponseMessage.MEMBER_SIGN_SUCCESS,
                entityModel
        );
    }

    @DeleteMapping(value = "/{memberId}")
    public ResponseEntity<ResponseDto> delete(@PathVariable Long memberId) {
        MemberDeleteBundle bundle = memberConverter.toMemberDeleteBundle(memberId);
        MemberDeleteResponse response = memberService.delete(bundle);

        // TODO
        EntityModel<MemberDeleteResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().slash(response.getMemberId()).withSelfRel().withType(HttpMethod.DELETE.name()),
                getLinkToAddress().withRel(INSERT_METHOD).withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getMemberId()).withRel(GET_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(GET_ALL_METHOD).withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.MEMBER_DELETE_SUCCESS,
                entityModel
        );
    }

    @PutMapping(value = "/{memberId}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> edit(@PathVariable Long memberId, @RequestBody MemberUpdateRequest request) {
        MemberUpdateBundle bundle = memberConverter.toMemberUpdateBundle(memberId, request);
        MemberUpdateResponse response = memberService.update(bundle);

        // TODO
        EntityModel<MemberUpdateResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().slash(response.getMemberId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getMemberId()).withRel(DELETE_METHOD).withType(HttpMethod.DELETE.name()),
                getLinkToAddress().slash(response.getMemberId()).withRel(GET_METHOD).withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel(GET_ALL_METHOD).withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.MEMBER_UPDATE_SUCCESS,
                entityModel
        );
    }

    @GetMapping(value = "/{memberId}")
    public ResponseEntity<ResponseDto> get(@PathVariable Long memberId) {
        MemberFindBundle bundle = memberConverter.toMemberFindBundle(memberId);
        MemberDetailResponse response = memberService.findById(bundle);

        // TODO
        EntityModel<MemberDetailResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().slash(response.getMemberId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getMemberId()).withRel(UPDATE_METHOD).withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getMemberId()).withRel(DELETE_METHOD).withType(HttpMethod.DELETE.name()),
                getLinkToAddress().withRel(GET_ALL_METHOD).withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.MEMBER_INQUIRY_SUCCESS,
                entityModel
        );
    }

    @GetMapping()
    public ResponseEntity<ResponseDto> getAll(Pageable pageable) {
        Page<MemberDetailResponse> response = memberService.findAll(pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.MEMBERS_INQUIRY_SUCCESS,
                response,
                link
        );
    }
}
