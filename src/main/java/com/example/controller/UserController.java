package com.example.controller;

import com.example.model.ResponseData;
import com.example.model.UserDto;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final HttpHeaders headers;

    @PostMapping("/users")
    ResponseEntity<?> createUser(@RequestBody @Validated UserDto dto) {
        UserDto data = userService.createUser(dto);
        return new ResponseEntity<>(new ResponseData<>(data), headers, HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    ResponseEntity<?> getUser(@PathVariable Long id) {
        UserDto data = userService.getUser(id);
        return new ResponseEntity<>(new ResponseData<>(data), headers, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Validated UserDto dto) {
        userService.updateUser(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
