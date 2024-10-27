package com.example.redis.controller;

import com.example.redis.dto.CreateUserDto;
import com.example.redis.dto.UpdateUserDto;
import com.example.redis.entity.User;
import com.example.redis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RedisTemplate<String, String> redisTemplate;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/id")
    public ResponseEntity<User> getUserById(@RequestParam Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestBody UpdateUserDto dto) {
        return ResponseEntity.ok(userService.updateUser(dto));
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUser(@RequestParam Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/test")
    public void rest() {
        try {
            redisTemplate.opsForValue().set("testKey", "testValue");
            String value = redisTemplate.opsForValue().get("testKey");
            System.out.println("Redis connection successful, retrieved value: " + value);
        } catch (Exception e) {
            System.err.println("Redis connection failed: " + e.getMessage());
        }
    }
}
