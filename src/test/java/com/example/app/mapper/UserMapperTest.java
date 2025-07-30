package com.example.app.mapper;

import com.example.app.domain.User;
import com.example.app.dto.UserRequestDto;
import com.example.app.dto.UserResponseDto;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(InstancioExtension.class)
class UserMapperTest {

    private final UserMapper mapper = new UserMapperImpl();

    @Test
    void from_dto_should_map_fields_and_set_encoded_password(
        @Given String name,
        @Given String email,
        @Given String password,
        @Given String encodedPassword) {

        UserRequestDto dto = new UserRequestDto(name, email, password);

        long before = System.currentTimeMillis();
        User user = mapper.fromDto(dto, encodedPassword);
        long after = System.currentTimeMillis();

        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(encodedPassword);
        assertThat(user.getId()).isBetween(before, after);
    }

    @Test
    void to_dto_should_map_fields_correctly(
        @Given long id,
        @Given String name,
        @Given String email,
        @Given String password) {

        User user = new User(id, name, email, password);

        UserResponseDto dto = mapper.toDto(user);

        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getEmail()).isEqualTo(email);
    }
}
