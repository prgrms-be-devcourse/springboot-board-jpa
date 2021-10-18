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

// TODO LINK를 자동화시키자!
// 헤이티오스를 분리하는건??? 컨트롤러에서는, 데이터만쏴주고, 헤이티오스를 담당하는 컨트롤러를 만들어서, 링크를 연결
// 메서드 공통부분 빼기, 어노테이션 인터셉터(스프링 컨테이너 관련)로..(aop도 쓸 필요없다..)
// 메서드 나누기, 어노테이션 만들기, 어떤 데이터를 넣을지, 리턴 값을 꺼내서 사용?? 찾아보기.. 영준님코드 보기
// aop 대신 인터셉터 로그 찍어주기!,어노테이션프로세서 -> 코드 컴파일할때 교체가능...

@RestController
@RequestMapping(value = "/api/member", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberConverter memberConverter;
    private final ResponseConverter responseConverter;

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
                getLinkToAddress().slash(response.getId()).withRel("delete").withType(HttpMethod.DELETE.name()),
                getLinkToAddress().slash(response.getId()).withRel("update").withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel("get").withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel("get-all").withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                HttpStatus.CREATED,
                ResponseMessage.MEMBER_SIGN_SUCCESS,
                entityModel
        );
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable Long id) {
        MemberDeleteBundle bundle = memberConverter.toMemberDeleteBundle(id);
        MemberDeleteResponse response = memberService.delete(bundle);

        // TODO
        EntityModel<MemberDeleteResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.DELETE.name()),
                getLinkToAddress().withRel("insert").withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(response.getId()).withRel("get").withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel("get-all").withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.MEMBER_DELETE_SUCCESS,
                entityModel
        );
    }

    @PutMapping(value = "/{id}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> edit(@PathVariable Long id, @RequestBody MemberUpdateRequest request) {
        MemberUpdateBundle bundle = memberConverter.toMemberUpdateBundle(id, request);
        MemberUpdateResponse response = memberService.update(bundle);

        // TODO
        EntityModel<MemberUpdateResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel("delete").withType(HttpMethod.DELETE.name()),
                getLinkToAddress().slash(response.getId()).withRel("get").withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel("get-all").withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.MEMBER_UPDATE_SUCCESS,
                entityModel
        );
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDto> get(@PathVariable Long id) {
        MemberFindBundle bundle = memberConverter.toMemberFindBundle(id);
        MemberDetailResponse response = memberService.findById(bundle);

        // TODO
        EntityModel<MemberDetailResponse> entityModel = EntityModel.of(response,
                getLinkToAddress().slash(response.getId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(response.getId()).withRel("update").withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(response.getId()).withRel("delete").withType(HttpMethod.DELETE.name()),
                getLinkToAddress().withRel("get-all").withType(HttpMethod.GET.name())
        );

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.MEMBER_INQUIRY_SUCCESS,
                entityModel
        );
    }

    @GetMapping()
    public ResponseEntity<ResponseDto> getAll(Pageable pageable) {
        Page<MemberDetailResponse> members = memberService.findAll(pageable);

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        return responseConverter.toResponseEntity(
                HttpStatus.OK,
                ResponseMessage.MEMBERS_INQUIRY_SUCCESS,
                members,
                link
        );
    }
}
