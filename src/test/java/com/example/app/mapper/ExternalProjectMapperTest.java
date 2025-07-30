package com.example.app.mapper;

import com.example.app.domain.ExternalProject;
import com.example.app.domain.ExternalProjectKey;
import com.example.app.dto.ExternalProjectRequestDto;
import com.example.app.dto.ExternalProjectResponseDto;
import java.util.List;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(InstancioExtension.class)
class ExternalProjectMapperTest {

    private final ExternalProjectMapper mapper = new ExternalProjectMapperImpl();

    @Test
    void to_dto_should_map_fields_correctly(@Given String id, @Given long userId, @Given String name) {

        ExternalProjectKey key = new ExternalProjectKey();
        key.setId(id);
        key.setUserId(userId);
        ExternalProject project = new ExternalProject(key, name);

        ExternalProjectResponseDto dto = mapper.toDto(project);

        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getUserId()).isEqualTo(userId);
        assertThat(dto.getName()).isEqualTo(name);
    }

    @Test
    void from_dto_should_map_fields_and_generate_id(@Given long userId, @Given String name) {

        ExternalProjectRequestDto dto = new ExternalProjectRequestDto(name);

        ExternalProject project = mapper.fromDto(dto, userId);

        assertThat(project.getName()).isEqualTo(name);
        assertThat(project.getKey().getUserId()).isEqualTo(userId);
        assertThat(project.getKey().getId()).isNotNull();
    }

    @Test
    void to_dto_list_should_return_list_of_dtos(@Given String id1, @Given long userId1,
        @Given String name1, @Given String id2, @Given long userId2, @Given String name2) {
        ExternalProjectKey key1 = new ExternalProjectKey();
        key1.setId(id1);
        key1.setUserId(userId1);
        ExternalProjectKey key2 = new ExternalProjectKey();
        key2.setId(id2);
        key2.setUserId(userId2);

        ExternalProject project1 = new ExternalProject(key1, name1);
        ExternalProject project2 = new ExternalProject(key2, name2);

        List<ExternalProjectResponseDto> dtos = mapper.toDtoList(List.of(project1, project2));

        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getId()).isEqualTo(id1);
        assertThat(dtos.get(1).getId()).isEqualTo(id2);
        assertThat(dtos.get(0).getName()).isEqualTo(name1);
        assertThat(dtos.get(1).getName()).isEqualTo(name2);
    }

    @Test
    void to_dto_list_should_return_empty_list_for_null_or_empty() {
        assertThat(mapper.toDtoList(null)).isEmpty();
        assertThat(mapper.toDtoList(List.of())).isEmpty();
    }
}
