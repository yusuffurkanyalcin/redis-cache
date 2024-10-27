package com.example.redis.service;

import com.example.redis.dto.CreateUserDto;
import com.example.redis.dto.UpdateUserDto;
import com.example.redis.entity.User;
import com.example.redis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Caches we want to delete
    @CacheEvict(value = {"users", "user_id"}, allEntries = true)
    public User createUser(CreateUserDto userDto) {
        User user = userRepository.save(userDto.toEntity(userDto));
        return user;
    }

    @Cacheable(cacheNames = "users", key = "#root.methodName", unless = "#result==null")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Cacheable(cacheNames = "user_id", key = "'getUserById' + #id", unless = "#result==null")
    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        System.out.println(user.getId());
        return user;
    }

    // Used for update existing data
    /* user_id
        |___> getUserById{id}
     */
    @CachePut(cacheNames = "user_id", key = "'getUserById' + #dto.id", unless = "#result==null")
    public User updateUser(UpdateUserDto dto) {
        Optional<User> updateUser = userRepository.findById(dto.getId());
        if (updateUser.isPresent()) {
            User user1 = updateUser.get();
            user1.setPassword(dto.getPassword());
            return userRepository.save(user1);
        }
        return null;
    }

    @CacheEvict(value = {"users", "user_id"}, allEntries = true)
    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User Deleted";
    }
}
