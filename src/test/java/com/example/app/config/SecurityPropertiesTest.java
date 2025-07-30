package com.example.app.config;

import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(InstancioExtension.class)
class SecurityPropertiesTest {

    @Test
    void create_security_properties(@Given String username, @Given String password, @Given String role) {
        SecurityProperties properties = new SecurityProperties(username, password, role);

        assertEquals(username, properties.username());
        assertEquals(password, properties.password());
        assertEquals(role, properties.role());
    }

}
