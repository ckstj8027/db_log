package com.example.db_log.repository;

import com.example.db_log.domain.SensitiveData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensitiveDataRepository extends JpaRepository<SensitiveData, Long> {
}
