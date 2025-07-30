package com.example.app.exception;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(InstancioExtension.class)
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handle_number_format_exception_should_return_bad_request(@Given String exceptionMessage) {
        NumberFormatException ex = new NumberFormatException(exceptionMessage);
        ResponseEntity<?> response = handler.handleNumberFormatException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(((Map<?, ?>) Objects.requireNonNull(response.getBody())).get("message"))
            .isEqualTo("Invalid user id format. Must be a number.");
    }

    @Test
    void handle_runtime_exception_should_return_internal_server_error(@Given String exceptionMessage) {
        RuntimeException ex = new RuntimeException(exceptionMessage);
        ResponseEntity<?> response = handler.handleRuntimeException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(((Map<?, ?>) Objects.requireNonNull(response.getBody())).get("message"))
            .isEqualTo("An unexpected error occurred.");
    }

    @Test
    void handle_user_not_found_should_return_not_found(@Given long userId) {
        UserNotFoundException ex = new UserNotFoundException(userId);
        ResponseEntity<?> response = handler.handleUserNotFound(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(((Map<?, ?>) Objects.requireNonNull(response.getBody())).get("message"))
            .isEqualTo(ex.getMessage());
    }

    @Test
    void handle_illegal_argument_should_return_bad_request(@Given String exceptionMessage) {
        IllegalArgumentException ex = new IllegalArgumentException(exceptionMessage);
        ResponseEntity<?> response = handler.handleIllegalArgument(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(((Map<?, ?>) Objects.requireNonNull(response.getBody())).get("message"))
            .isEqualTo(ex.getMessage());
    }

    @Test
    void handle_validation_exceptions_should_return_bad_request_and_errors() throws NoSuchMethodException {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "objectName");
        bindingResult.addError(new FieldError("objectName", "name", "must not be blank"));

        Method method = this.getClass().getDeclaredMethod("testMethod", String.class);
        MethodParameter methodParameter = new MethodParameter(method, 0);
        MethodArgumentNotValidException ex =
            new MethodArgumentNotValidException(methodParameter, bindingResult);

        ResponseEntity<Map<String, Object>> response = handler.handleValidationExceptions(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Map<String, Object> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.get("message")).isNotNull();
        assertThat(body).containsKey("errors");
        Map<String, String> errors = (Map<String, String>) body.get("errors");
        assertThat(errors).containsEntry("name", "must not be blank");
    }

    @Test
    void handle_general_exception_should_return_internal_server_error() {
        Exception ex = new Exception("Generic error");
        ResponseEntity<?> response = handler.handleGeneralException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(((Map<?, ?>) response.getBody()).get("message"))
            .isEqualTo("An unexpected error occurred.");
    }

    private void testMethod(String param) {
    }
}
