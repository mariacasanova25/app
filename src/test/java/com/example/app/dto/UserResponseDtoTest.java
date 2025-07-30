package com.example.app.dto;

import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(InstancioExtension.class)
class UserResponseDtoTest {

    @Test
    void create_user_response_dto(@Given long userId, @Given String name, @Given String email) {
        UserResponseDto user = new UserResponseDto(userId, name, email);

        assertEquals(userId, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
    }

}
