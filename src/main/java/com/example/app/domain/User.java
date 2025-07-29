package com.example.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("tb_users")
@Data
@AllArgsConstructor
public class User {
    @PrimaryKey
    private long id;
    private String name;
    private String email;
    private String password;
}
