package com.example.app.service;

import com.example.app.domain.User;
import com.example.app.dto.UserRequestDto;
import com.example.app.dto.UserResponseDto;
import com.example.app.exception.UserNotFoundException;
import com.example.app.mapper.UserMapper;
import com.example.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDto findUserById(long userId) {

        return userRepository.findById(userId)
            .map(user -> {
                log.info("Found user with id: {}", userId);
                return userMapper.toDto(user);
            })
            .orElseThrow(() -> {
                log.error("User with id {} not found", userId);
                return new UserNotFoundException(userId);
            });
    }

    public UserResponseDto saveUser(UserRequestDto userRequestDto) {

        User user = userMapper.fromDto(userRequestDto, passwordEncoder.encode(userRequestDto.getPassword()));
        User savedUser = userRepository.save(user);
        log.info("Saved user with id: {}", savedUser.getId());
        return userMapper.toDto(savedUser);
    }

    public void deleteUser(long userId) {
        if (!userRepository.existsById(userId)) {
            log.error("User with id {} not found for deletion", userId);
            throw new UserNotFoundException(userId);
        }
        userRepository.deleteById(userId);
        log.info("Deleted user with id {}", userId);
    }

}
