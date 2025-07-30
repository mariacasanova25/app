package com.example.app.controller;

import com.example.app.dto.UserRequestDto;
import com.example.app.dto.UserResponseDto;
import com.example.app.mapper.UserMapper;
import com.example.app.service.UserService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void get_user_by_id_should_return_user_response_dto() {
        long userId = Instancio.create(Long.class);
        UserResponseDto responseDto = Instancio.create(UserResponseDto.class);

        when(userService.findUserById(userId)).thenReturn(responseDto);

        ResponseEntity<UserResponseDto> result = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(userService, times(1)).findUserById(userId);
    }

    @Test
    void create_user_should_return_user_response_dto() {
        UserRequestDto requestDto = Instancio.create(UserRequestDto.class);
        UserResponseDto responseDto = Instancio.create(UserResponseDto.class);

        when(userService.saveUser(requestDto)).thenReturn(responseDto);

        ResponseEntity<UserResponseDto> result = userController.createUser(requestDto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(userService, times(1)).saveUser(requestDto);
    }

    @Test
    void delete_user_should_return_no_content() {
        long userId = Instancio.create(Long.class);

        ResponseEntity<Void> result = userController.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }
}
