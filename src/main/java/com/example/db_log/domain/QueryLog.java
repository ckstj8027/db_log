package com.example.db_log.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class QueryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionId;
    private String clientIp;
    private String query;
    private LocalDateTime timestamp;
    private long executionTime;
    private int resultRowCount;

    public QueryLog(String sessionId, String clientIp, String query, long executionTime, int resultRowCount) {
        this.sessionId = sessionId;
        this.clientIp = clientIp;
        this.query = query;
        this.timestamp = LocalDateTime.now();
        this.executionTime = executionTime;
        this.resultRowCount = resultRowCount;
    }
}
