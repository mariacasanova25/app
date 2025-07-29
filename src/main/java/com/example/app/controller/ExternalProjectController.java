package com.example.app.controller;

import com.example.app.dto.ExternalProjectRequestDto;
import com.example.app.dto.ExternalProjectResponseDto;
import com.example.app.service.ExternalProjectService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExternalProjectController {

    private final ExternalProjectService externalProjectService;

    @PostMapping("/users/{id}/external/projects")
    public ResponseEntity<ExternalProjectResponseDto> createExternalProject(@PathVariable long id, @Valid @RequestBody ExternalProjectRequestDto projectDto) {

        ExternalProjectResponseDto externalProjectResponseDto = externalProjectService.addExternalProject(id, projectDto);
        return ResponseEntity.ok(externalProjectResponseDto);
    }

    @GetMapping("/users/{id}/external/projects")
    public ResponseEntity<List<ExternalProjectResponseDto>> getExternalProjectByUserId(@PathVariable long id) {

        List<ExternalProjectResponseDto> externalProjectsByUserId = externalProjectService.getExternalProjectsByUserId(id);

        return ResponseEntity.ok(externalProjectsByUserId);
    }
}
