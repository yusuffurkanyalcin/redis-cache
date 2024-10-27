package com.example.redis.dto;

import com.example.redis.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {

    private Long id;
    private String password;

    public User toEntity(UpdateUserDto dto) {
        return User.builder()
                .id(dto.getId())
                .password(dto.getPassword())
                .build();
    }
}
