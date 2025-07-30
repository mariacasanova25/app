package com.example.app.service;

import com.example.app.domain.User;
import com.example.app.dto.UserRequestDto;
import com.example.app.dto.UserResponseDto;
import com.example.app.exception.UserNotFoundException;
import com.example.app.mapper.UserMapper;
import com.example.app.repository.UserRepository;
import java.util.Optional;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, InstancioExtension.class})
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @Test
    void find_user_by_id_should_return_user_response_dto_when_user_exists(
        @Given long userId,
        @Given User user,
        @Given UserResponseDto responseDto
    ) {

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(responseDto);

        UserResponseDto result = userService.findUserById(userId);

        assertEquals(responseDto, result);
        verify(userRepository).findById(userId);
        verify(userMapper).toDto(user);
    }

    @Test
    void find_user_by_id_should_throw_when_user_does_not_exist(@Given long userId) {

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findUserById(userId));
        verify(userRepository).findById(userId);
        verifyNoInteractions(userMapper);
    }

    @Test
    void save_user_should_encode_password_and_return_user_response_dto(
        @Given UserRequestDto requestDto,
        @Given String encodedPassword,
        @Given User user,
        @Given User savedUser,
        @Given UserResponseDto responseDto
    ) {

        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn(encodedPassword);
        when(userMapper.fromDto(requestDto, encodedPassword)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(responseDto);

        UserResponseDto result = userService.saveUser(requestDto);

        assertEquals(responseDto, result);

        verify(passwordEncoder).encode(requestDto.getPassword());
        verify(userMapper).fromDto(requestDto, encodedPassword);
        verify(userRepository).save(user);
        verify(userMapper).toDto(savedUser);
    }

    @Test
    void delete_user_should_delete_when_user_exists(@Given long userId) {

        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUser(userId);

        verify(userRepository).existsById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void delete_user_should_throw_when_user_does_not_exist(@Given long userId) {

        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));

        verify(userRepository).existsById(userId);
        verify(userRepository, never()).deleteById(anyLong());
    }
}
