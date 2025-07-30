package com.example.app.exception;

import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(InstancioExtension.class)
class UserNotFoundExceptionTest {

    @Test
    void should_create_user_not_found_exception_with_valid_user_id(@Given Long userId) {
        UserNotFoundException exception = new UserNotFoundException(userId);
        assertThat(exception)
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("User not found with id: " + userId);
    }

}
