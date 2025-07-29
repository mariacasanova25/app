package com.example.app.service;

import com.example.app.domain.ExternalProject;
import com.example.app.dto.ExternalProjectRequestDto;
import com.example.app.dto.ExternalProjectResponseDto;
import com.example.app.exception.UserNotFoundException;
import com.example.app.mapper.ExternalProjectMapper;
import com.example.app.repository.ExternalProjectRepository;
import com.example.app.repository.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExternalProjectService {

    @Autowired
    private ExternalProjectRepository externalProjectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExternalProjectMapper externalProjectMapper;

    public ExternalProjectResponseDto addExternalProject(long userId, ExternalProjectRequestDto externalProjectRequestDto) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        ExternalProject externalProject = externalProjectMapper.fromDto(externalProjectRequestDto, userId);

        log.info("Added external project: {}", externalProject.getName());

        return externalProjectMapper.toDto(externalProjectRepository.save(externalProject));
    }

    public List<ExternalProjectResponseDto> getExternalProjectsByUserId(long id) {
        if (!userRepository.existsById(id)) {
            log.warn("User with id {} does not exist", id);
            throw new UserNotFoundException(id);
        }

        List<ExternalProject> projects = externalProjectRepository.findByKeyUserId(id);
        return externalProjectMapper.toDtoList(projects);
    }
}
