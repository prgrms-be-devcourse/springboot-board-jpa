package org.programmers.springbootboardjpa.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootboardjpa.web.dto.user.LongIdResponse;
import org.programmers.springbootboardjpa.web.dto.user.UserCreateFormV1;
import org.programmers.springbootboardjpa.web.dto.user.UserDtoV1;
import org.programmers.springbootboardjpa.web.dto.user.UserUpdateFormV1;
import org.programmers.springbootboardjpa.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiControllerV1 {

    private final UserService userService;


    @PostMapping("/api/v1/users")
    public ResponseEntity<LongIdResponse> postUser(@RequestBody UserCreateFormV1 userCreateForm) {
        Long id = userService.registerUser(userCreateForm);
        //TODO PR 포인트: SingletonMap?
        //TODO PR 포인트: 사용자 ID 값은 데이터베이스에서 사용자를 식별할 수 있는 값인데, 노출하면 안 되지 않나? (개인정보보호법) 일단 카카오는 회원 ID를 넘겨줌
        return ResponseEntity.created(URI.create("/api/v1/users/" + id)).body(new LongIdResponse(id));
    }

    //TODO: REST 스타일이 나은지, me -> 연결되게 하는 것이 나은지
    @GetMapping("/api/v1/users/{id}")
    public UserDtoV1 showUserDetails(@PathVariable("id") Long userId) {
        //TODO: 사용자가 설정한 공개 범위에 따라 다르게 공개
        return UserDtoV1.from(userService.findUserWith(userId));
    }

    @PutMapping("/api/v1/users/{id}")
    public UserDtoV1 setUserData(@PathVariable("id") Long userId, @RequestBody UserUpdateFormV1 userUpdateForm) {
        return UserDtoV1.from(userService.modifyUserdata(userUpdateForm));
    }

    @DeleteMapping("/api/v1/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/users")
    public Page<UserDtoV1> showUsersPaged(Pageable pageable) {
        return userService.findUsersWithPage(pageable).map(UserDtoV1::from);
    }
}