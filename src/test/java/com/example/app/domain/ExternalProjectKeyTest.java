package com.example.app.domain;

import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(InstancioExtension.class)
class ExternalProjectKeyTest {

    @Test
    void create_external_project_key(@Given String id, @Given Long userId) {
        ExternalProjectKey externalProjectKey = new ExternalProjectKey();
        externalProjectKey.setId(id);
        externalProjectKey.setUserId(userId);

        assertEquals(id, externalProjectKey.getId());
        assertEquals(userId, externalProjectKey.getUserId());
    }

}
