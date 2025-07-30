package com.example.app.service;

import com.example.app.domain.ExternalProject;
import com.example.app.dto.ExternalProjectRequestDto;
import com.example.app.dto.ExternalProjectResponseDto;
import com.example.app.exception.UserNotFoundException;
import com.example.app.mapper.ExternalProjectMapper;
import com.example.app.repository.ExternalProjectRepository;
import com.example.app.repository.UserRepository;
import java.util.List;
import org.instancio.Instancio;
import org.instancio.junit.Given;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, InstancioExtension.class})
class ExternalProjectServiceTest {

    @Mock
    private ExternalProjectRepository externalProjectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExternalProjectMapper externalProjectMapper;

    @InjectMocks
    private ExternalProjectService externalProjectService;

    @Test
    void add_external_project_should_return_response_dto_when_user_exists(
        @Given long userId,
        @Given ExternalProjectRequestDto requestDto,
        @Given ExternalProjectResponseDto responseDto,
        @Given ExternalProject domain,
        @Given ExternalProject saved) {

        when(userRepository.existsById(userId)).thenReturn(true);
        when(externalProjectMapper.fromDto(requestDto, userId)).thenReturn(domain);
        when(externalProjectRepository.save(domain)).thenReturn(saved);
        when(externalProjectMapper.toDto(saved)).thenReturn(responseDto);

        ExternalProjectResponseDto result = externalProjectService.addExternalProject(userId, requestDto);

        assertEquals(responseDto, result);
        verify(userRepository).existsById(userId);
        verify(externalProjectMapper).fromDto(requestDto, userId);
        verify(externalProjectRepository).save(domain);
        verify(externalProjectMapper).toDto(saved);
    }

    @Test
    void add_external_project_should_throw_exception_when_user_does_not_exist(@Given long userId, @Given ExternalProjectRequestDto requestDto) {

        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> externalProjectService.addExternalProject(userId, requestDto));
        verify(userRepository).existsById(userId);
        verifyNoInteractions(externalProjectMapper, externalProjectRepository);
    }

    @Test
    void get_external_projects_by_user_id_should_return_list_when_user_exists(@Given long userId) {

        List<ExternalProject> projects = Instancio.ofList(ExternalProject.class).size(2).create();
        List<ExternalProjectResponseDto> dtoList = Instancio.ofList(ExternalProjectResponseDto.class).size(2).create();

        when(userRepository.existsById(userId)).thenReturn(true);
        when(externalProjectRepository.findByKeyUserId(userId)).thenReturn(projects);
        when(externalProjectMapper.toDtoList(projects)).thenReturn(dtoList);

        List<ExternalProjectResponseDto> result = externalProjectService.getExternalProjectsByUserId(userId);

        assertEquals(dtoList, result);
        verify(userRepository).existsById(userId);
        verify(externalProjectRepository).findByKeyUserId(userId);
        verify(externalProjectMapper).toDtoList(projects);
    }

    @Test
    void get_external_projects_by_user_id_should_throw_exception_when_user_does_not_exist(@Given long userId) {

        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> externalProjectService.getExternalProjectsByUserId(userId));
        verify(userRepository).existsById(userId);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(externalProjectRepository, externalProjectMapper);
    }
}
