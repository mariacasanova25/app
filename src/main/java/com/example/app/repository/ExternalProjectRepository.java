package com.example.app.repository;

import com.example.app.domain.ExternalProject;
import com.example.app.domain.ExternalProjectKey;
import java.util.List;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface ExternalProjectRepository extends CassandraRepository<ExternalProject, ExternalProjectKey> {

    List<ExternalProject> findByKeyUserId(long id);
}
