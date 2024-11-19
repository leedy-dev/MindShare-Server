package com.mindshare.ams.domain.user.controller;

import com.mindshare.ams.domain.user.service.UserService;
import com.mindshare.ams.domain.user.service.dto.UserRequestDto;
import com.mindshare.core.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ams/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto.Response> doGetUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<UserDto.Response> doCreateUser(
            @Validated @RequestBody UserRequestDto.Join userRequestDto,
            BindingResult bindingResult) {
        return new ResponseEntity<>(userService.createUser(userRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto.Response> doUpdateUser(
            @PathVariable("id") Long id,
            @Validated @RequestBody UserRequestDto.Update userRequestDto,
            BindingResult bindingResult) {
        return new ResponseEntity<>(userService.updateUser(id, userRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> doDeleteUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.deleteUserById(id), HttpStatus.OK);
    }

}
