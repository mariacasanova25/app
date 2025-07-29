package com.example.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExternalProjectResponseDto {
    private String id;
    private long userId;
    private String name;
}
