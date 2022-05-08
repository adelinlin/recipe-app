package com.pinteaadelin.recipe.model.response;

import com.pinteaadelin.recipe.model.Role;
import com.pinteaadelin.recipe.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static java.util.stream.Collectors.joining;

@Getter
@Setter
@ToString
public class UserResponseDto {

    private String username;
    private String roles;

    public static UserResponseDto from(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(joining(",")));
        return userResponseDto;
    }

}
