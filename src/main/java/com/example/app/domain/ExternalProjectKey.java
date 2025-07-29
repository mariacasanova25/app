package com.example.app.domain;

import java.io.Serializable;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@Data
@PrimaryKeyClass
public class ExternalProjectKey implements Serializable {

    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.CLUSTERED)
    private String id;
    @PrimaryKeyColumn(name = "user_id", type = PrimaryKeyType.PARTITIONED)
    private long userId;
}
