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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(InstancioExtension.class)
class UserRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void should_pass_validation_with_valid_data(@Given String name, @Given String password) {
        UserRequestDto dto = new UserRequestDto(name, name + "@example.com", password);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "Validation should pass for valid input");
    }

    @Test
    void should_fail_validation_when_name_is_blank(@Given String name, @Given String password) {
        UserRequestDto dto = new UserRequestDto("  ", name + "@example.com", password);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    void should_fail_validation_when_email_is_blank(@Given String name, @Given String password) {
        UserRequestDto dto = new UserRequestDto(name, "  ", password);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email"));
    }

    @Test
    void should_fail_validation_when_email_is_invalid_format(@Given String name, @Given String password) {
        UserRequestDto dto = new UserRequestDto(name, "invalid-email", password);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertThat(violations)
            .anyMatch(v -> v.getPropertyPath().toString().equals("email")
                && v.getMessage().contains("Invalid email format"));
    }

    @Test
    void should_throw_exception_when_name_is_null(@Given String name, @Given String password) {
        assertThatThrownBy(() -> new UserRequestDto(null, name + "@example.com", password))
            .isInstanceOf(NullPointerException.class)
            .hasMessageContaining("name");
    }

    @Test
    void should_throw_exception_when_mail_is_null(@Given String name, @Given String password) {
        assertThatThrownBy(() -> new UserRequestDto(name, null, password))
            .isInstanceOf(NullPointerException.class)
            .hasMessageContaining("mail");
    }

    @Test
    void should_throw_exception_when_password_is_null(@Given String name) {
        assertThatThrownBy(() -> new UserRequestDto(name, name + "@example.com", null))
            .isInstanceOf(NullPointerException.class)
            .hasMessageContaining("password");
    }

}
