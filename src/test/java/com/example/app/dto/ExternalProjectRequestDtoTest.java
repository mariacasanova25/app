package com.example.app.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
@ExtendWith(InstancioExtension.class)
class ExternalProjectRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void should_create_external_project_request_dto_with_valid_name(@Given String name) {

        ExternalProjectRequestDto dto = new ExternalProjectRequestDto(name);

        assertThat(dto)
            .isInstanceOf(ExternalProjectRequestDto.class)
            .hasFieldOrPropertyWithValue("name", name);
    }

    @Test
    void should_fail_validation_when_name_is_blank() {
        ExternalProjectRequestDto dto = new ExternalProjectRequestDto(" ");

        Set<ConstraintViolation<ExternalProjectRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Validation should fail for blank name");
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }
}
