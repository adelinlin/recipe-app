package com.pinteaadelin.recipe.controller;

import com.pinteaadelin.recipe.model.User;
import com.pinteaadelin.recipe.model.request.UserRequestDto;
import com.pinteaadelin.recipe.model.response.UserResponseDto;
import com.pinteaadelin.recipe.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.pinteaadelin.recipe.model.response.UserResponseDto.from;
import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        User user = userService.createUser(userRequestDto, userRequestDto.getRole());
        return new ResponseEntity<>(from(user), CREATED);
    }

}
