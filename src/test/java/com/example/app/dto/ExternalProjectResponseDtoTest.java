package com.example.app.dto;

import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(InstancioExtension.class)
class ExternalProjectResponseDtoTest {

    @Test
    void should_create_external_project_response_dto_with_valid_data(@Given String id, @Given long userId, @Given String name) {

        ExternalProjectResponseDto dto = new ExternalProjectResponseDto(id, userId, name);

        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getUserId()).isEqualTo(userId);
        assertThat(dto.getName()).isEqualTo(name);
    }
}
