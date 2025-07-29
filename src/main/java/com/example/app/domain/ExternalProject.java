package com.example.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("tb_external_projects")
@AllArgsConstructor
@Data
public class ExternalProject {
    @PrimaryKey
    private ExternalProjectKey key;

    private String name;
}
