package yjh.jpa.springnoticeboard.domain.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yjh.jpa.springnoticeboard.domain.ApiResponse;
import yjh.jpa.springnoticeboard.domain.dto.UserDto;
import yjh.jpa.springnoticeboard.domain.service.UserService;

@RestController
@RequestMapping(path = "board/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "{userId}")
    public ApiResponse<UserDto> getUser(@PathVariable(name="userId") Long userId) throws NotFoundException {
        UserDto user = userService.findUser(userId);
        return ApiResponse.ok(user);
    }

    @PostMapping(path = "")
    public ApiResponse<Long> insertUser(@RequestBody UserDto userDto){
        Long userId = userService.createUser(userDto);
        return ApiResponse.ok(userId);
    }

    @PutMapping(path = "{userId}")
    public ApiResponse<Long> updateUser(@PathVariable(name = "userId") Long userId, @RequestBody UserDto userDto) throws NotFoundException {
        Long update = userService.updateUser(userId, userDto);
        return ApiResponse.ok(update);
    }

    @GetMapping(path = "")
    public ApiResponse<Page<Object>> getUserList(Pageable pageable){
        Page<Object> userList = userService.findAll(pageable);
        return ApiResponse.ok(userList);
    }

    @DeleteMapping(path = "{userId}")
    public void removeUser(@PathVariable(name = "userId") Long userId){
        userService.deleteUser(userId);
    }

    @DeleteMapping(path = "")
    public void removeUserList(){
        userService.deleteAll();
    }
}
