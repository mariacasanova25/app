package com.example.app.controller;

import com.example.app.dto.ExternalProjectRequestDto;
import com.example.app.dto.ExternalProjectResponseDto;
import com.example.app.service.ExternalProjectService;
import java.util.List;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({InstancioExtension.class, MockitoExtension.class})
class ExternalProjectControllerTest {

    @Mock
    private ExternalProjectService externalProjectService;

    @InjectMocks
    private ExternalProjectController externalProjectController;

    @Test
    void create_external_project_should_return_response() {
        long userId = Instancio.create(Long.class);
        ExternalProjectRequestDto requestDto = Instancio.create(ExternalProjectRequestDto.class);
        ExternalProjectResponseDto responseDto = Instancio.create(ExternalProjectResponseDto.class);

        when(externalProjectService.addExternalProject(userId, requestDto)).thenReturn(responseDto);

        ResponseEntity<ExternalProjectResponseDto> result =
            externalProjectController.createExternalProject(userId, requestDto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(externalProjectService, times(1)).addExternalProject(userId, requestDto);
    }

    @Test
    void get_external_project_by_user_id_should_return_list() {
        long userId = Instancio.create(Long.class);
        List<ExternalProjectResponseDto> responseList = Instancio.ofList(ExternalProjectResponseDto.class).size(2).create();

        when(externalProjectService.getExternalProjectsByUserId(userId)).thenReturn(responseList);

        ResponseEntity<List<ExternalProjectResponseDto>> result =
            externalProjectController.getExternalProjectByUserId(userId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
        verify(externalProjectService, times(1)).getExternalProjectsByUserId(userId);
    }
}
