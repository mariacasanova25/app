package com.example.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExternalProjectRequestDto {

    @NotBlank
    private String name;
}
