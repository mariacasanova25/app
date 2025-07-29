package com.example.app.mapper;

import com.example.app.domain.ExternalProject;
import com.example.app.domain.ExternalProjectKey;
import com.example.app.dto.ExternalProjectRequestDto;
import com.example.app.dto.ExternalProjectResponseDto;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExternalProjectMapper {

    default ExternalProjectResponseDto toDto(ExternalProject project) {
        return new ExternalProjectResponseDto(
            project.getKey().getId(),
            project.getKey().getUserId(),
            project.getName()
        );
    }

    default ExternalProject fromDto(ExternalProjectRequestDto projectDto, long userId) {
        ExternalProjectKey externalProjectKey = new ExternalProjectKey();
        externalProjectKey.setId(UUID.randomUUID().toString());
        externalProjectKey.setUserId(userId);
        return new ExternalProject(externalProjectKey, projectDto.getName());
    }

    default List<ExternalProjectResponseDto> toDtoList(List<ExternalProject> projects) {

        if (projects == null || projects.isEmpty()) {
            return List.of();
        }
        return projects.stream().map(externalProject ->
            new ExternalProjectResponseDto(
                externalProject.getKey().getId(),
                externalProject.getKey().getUserId(),
                externalProject.getName()
            )
        ).toList();
    }
}
