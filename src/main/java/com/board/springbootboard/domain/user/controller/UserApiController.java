package com.board.springbootboard.domain.user.controller;

import com.board.springbootboard.domain.user.dto.UserResponseDto;
import com.board.springbootboard.domain.user.dto.UserSaveRequestDto;
import com.board.springbootboard.domain.user.dto.UserUpdateRequestDto;
import com.board.springbootboard.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    // 회원 등록
    @PostMapping("/api/v1/users")
    public Long save(@RequestBody UserSaveRequestDto requestDto) {
        return userService.save(requestDto);
    }

    // id로 수정
    @PutMapping("/api/v1/users/{id}")
    public Long update(@PathVariable Long id, @RequestBody UserUpdateRequestDto requestsDto) {
        return userService.update(id,requestsDto);
    }

    // id로 조회
    @GetMapping("/api/v1/users/{id}")
    public UserResponseDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }


    // TODO 다건 조회 : VIEW 단 만들어서 연결 예정
//    @GetMapping("/")
//    public String index(Model model) {
//        model.addAttribute("users",userService.findAllDesc());
//        return "index";
//    }

    // TODO nickName으로 회원 조회 및 수정





}
