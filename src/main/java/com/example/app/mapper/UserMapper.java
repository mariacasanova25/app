package com.example.app.mapper;

import com.example.app.domain.User;
import com.example.app.dto.UserRequestDto;
import com.example.app.dto.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    default User fromDto(UserRequestDto userRequestDto, String encodedPassword) {
        return new User(
            System.currentTimeMillis(),
            userRequestDto.getName(),
            userRequestDto.getEmail(),
            encodedPassword
        );
    }

    UserResponseDto toDto(User user);
}
