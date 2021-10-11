package com.programmers.springbootboard.member.presentation;

import com.programmers.springbootboard.common.dto.ResponseDto;
import com.programmers.springbootboard.common.dto.ResponseMessage;
import com.programmers.springbootboard.member.application.MemberService;
import com.programmers.springbootboard.member.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 어노테이션프로세서 -> 코드 컴파일할때 교체가능...

// 다음주 점퍼 보여드리기
// @NonNull과 @Notnull  차이점
// Controller와 Service 명칭 정하기 찾아오기!! 다음주까지!! 네이밍 규차ㅣㄱ

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

// TODO LINK를 자동화시키자!
// 헤이티오스를 분리하는건??? 컨트롤러와, 데이터만쏴주고, 헤이티오스를 담당하는 컨트롤러를 만들어서, 링크를 연결
// 메서드 공통부분 빼기, 어노테이션 인터셉터(스프링 컨테이너 관련)로..(aop도 쓸 필요없다..)
// 메서드 나누기, 어노테이션 만들기, 어떤 데이터를 넣을지, 리턴 값을 꺼내서 사용?? 찾아보기.. 영준님코드 보기
// aop 대신 인터셉터 로그 찍어주기!


@RestController
@RequestMapping(value = "/api/member", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    private WebMvcLinkBuilder getLinkToAddress() {
        return linkTo(MemberController.class);
    }

    @PostMapping(consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> insertMember(@RequestBody MemberSignRequest request) {
        MemberDetailResponse member = memberService.insert(request);

        EntityModel<MemberDetailResponse> entityModel = EntityModel.of(member,
                getLinkToAddress().withSelfRel().withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(member.getId()).withRel("delete").withType(HttpMethod.DELETE.name()),
                getLinkToAddress().slash(member.getId()).withRel("update").withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(member.getId()).withRel("get").withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel("get-all").withType(HttpMethod.GET.name())
        );

        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_SIGN_SUCCESS, entityModel));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseDto> deleteMember(@PathVariable Long id) {
        MemberDeleteResponse member = memberService.deleteById(id);

        EntityModel<MemberDeleteResponse> entityModel = EntityModel.of(member,
                getLinkToAddress().slash(member.getId()).withSelfRel().withType(HttpMethod.DELETE.name()),
                getLinkToAddress().withRel("insert").withType(HttpMethod.POST.name()),
                getLinkToAddress().slash(member.getId()).withRel("get").withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel("get-all").withType(HttpMethod.GET.name())
        );

        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_DELETE_SUCCESS, entityModel));
    }

    @PatchMapping(value = "/{id}", consumes = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<ResponseDto> updateMember(@PathVariable Long id, @RequestBody MemberUpdateRequest request) {
        MemberDetailResponse member = memberService.update(id, request);

        EntityModel<MemberDetailResponse> entityModel = EntityModel.of(member,
                getLinkToAddress().slash(member.getId()).withSelfRel().withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(member.getId()).withRel("delete").withType(HttpMethod.DELETE.name()),
                getLinkToAddress().slash(member.getId()).withRel("get").withType(HttpMethod.GET.name()),
                getLinkToAddress().withRel("get-all").withType(HttpMethod.GET.name())
        );

        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_UPDATE_SUCCESS, entityModel));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDto> member(@PathVariable Long id) {
        MemberDetailResponse member = memberService.findById(id);

        EntityModel<MemberDetailResponse> entityModel = EntityModel.of(member,
                getLinkToAddress().slash(member.getId()).withSelfRel().withType(HttpMethod.GET.name()),
                getLinkToAddress().slash(member.getId()).withRel("update").withType(HttpMethod.PATCH.name()),
                getLinkToAddress().slash(member.getId()).withRel("delete").withType(HttpMethod.DELETE.name()),
                getLinkToAddress().withRel("get-all").withType(HttpMethod.GET.name())
        );

        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_INQUIRY_SUCCESS, entityModel));
    }

    @GetMapping()
    public ResponseEntity<ResponseDto> members() {
        List<MemberDetailResponse> members = memberService.findAll();

        Link link = getLinkToAddress().withSelfRel().withType(HttpMethod.GET.name());

        // responseDto와 Link를 따로두기 이걸 래핑할 또다른 객체 생성
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.MEMBER_INQUIRY_SUCCESS, members, link));
    }
}
