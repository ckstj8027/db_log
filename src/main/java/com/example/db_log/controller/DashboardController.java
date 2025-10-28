package com.example.db_log.controller;

import com.example.db_log.domain.QueryLog;
import com.example.db_log.repository.QueryLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final QueryLogRepository queryLogRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<QueryLog> allLogs = queryLogRepository.findAll();

        // Group logs by session ID to calculate intervals correctly
        Map<String, List<QueryLog>> logsBySession = allLogs.stream()
                .collect(Collectors.groupingBy(QueryLog::getSessionId));

        List<Map<String, Object>> vectorData = new ArrayList<>();

        // For each session, sort logs by timestamp and calculate intervals
        for (List<QueryLog> sessionLogs : logsBySession.values()) {
            // Sort logs chronologically
            sessionLogs.sort(Comparator.comparing(QueryLog::getTimestamp));

            QueryLog previousLog = null;
            for (QueryLog currentLog : sessionLogs) {
                long interval = 0;
                // Calculate time difference with the previous query in the same session
                if (previousLog != null) {
                    interval = Duration.between(previousLog.getTimestamp(), currentLog.getTimestamp()).toMillis();
                }

                Map<String, Object> point = new HashMap<>();
                point.put("x", currentLog.getExecutionTime());   // X-axis: Execution Time (ms)
                point.put("y", currentLog.getResultRowCount()); // Y-axis: Result Row Count
                point.put("z", interval);                       // Z-axis: Query Interval (ms)
                point.put("text", currentLog.getQuery());       // Hover text for context

                vectorData.add(point);
                previousLog = currentLog;
            }
        }

        model.addAttribute("vectorData", vectorData);
        return "dashboard";
    }
}
