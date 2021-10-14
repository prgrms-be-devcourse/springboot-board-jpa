package com.example.board.member;

import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @PostMapping("/join")
    public MemberDto createMember(@RequestBody MemberDto memberDto){
        return service.createMember(memberDto);
    }

    @GetMapping("/members/{name}")
    public MemberDto findMemberByName(@PathVariable String name){
        return service.findByName(name);
    }

    @GetMapping("/delete/{name}")
    public RuntimeException deleteMember(@PathVariable String name){
        return service.deleteMember(name);
    }

    @PostMapping("/update")
    public MemberDto updateMember(@RequestBody MemberDto memberDto){
        return service.updateMember(memberDto);
    }
}
