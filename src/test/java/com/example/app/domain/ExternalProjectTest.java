package com.example.app.domain;

import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(InstancioExtension.class)
class ExternalProjectTest {

    @Test
    void create_external_project(@Given String id, @Given Long userId, @Given String name) {
        ExternalProjectKey key = new ExternalProjectKey();
        key.setId(id);
        key.setUserId(userId);

        ExternalProject project = new ExternalProject(key, name);

        assertEquals(id, project.getKey().getId());
        assertEquals(userId, project.getKey().getUserId());
        assertEquals(name, project.getName());
    }

}
