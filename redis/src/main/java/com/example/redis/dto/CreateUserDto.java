package com.example.redis.dto;

import com.example.redis.entity.User;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUserDto {

    private String username;
    private String password;

    public User toEntity(CreateUserDto userDto) {
        return User.builder()
                .name(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
    }
}
