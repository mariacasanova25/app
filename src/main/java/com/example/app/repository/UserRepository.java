package com.example.app.repository;

import com.example.app.domain.User;
import java.util.Optional;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserRepository extends CassandraRepository<User, Long> {
    Optional<User> findByEmail(String username);
}
