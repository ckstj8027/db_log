package com.example.db_log.controller;

import com.example.db_log.domain.SensitiveData;
import com.example.db_log.domain.User;
import com.example.db_log.repository.SensitiveDataRepository;
import com.example.db_log.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final UserRepository userRepository;
    private final SensitiveDataRepository sensitiveDataRepository;

    @PostConstruct
    public void init() {
        // 초기 데이터 생성 (최초 실행 시에만 동작)
        if (userRepository.count() == 0) {
            for (int i = 0; i < 100; i++) {
                User user = new User("user" + i, "password" + i);
                userRepository.save(user);
                sensitiveDataRepository.save(new SensitiveData(user, "sensitive_data_" + i));
            }
        }
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/sensitive-data")
    public List<SensitiveData> getAllSensitiveData() {
        return sensitiveDataRepository.findAll();
    }

    // --- 테스트용 API: 대시보드에서 시각적으로 이상 징후를 확인하기 위함 ---

    @GetMapping("/test/abnormal-access")
    public String abnormalAccess() {
        for (int i = 0; i < 20; i++) {
            userRepository.findAll();
        }
        return "Simulated abnormal access (20 queries). Check the 3D dashboard.";
    }

    @GetMapping("/test/massive-query")
    public String massiveQuery() {
        sensitiveDataRepository.findAll();
        return "Simulated massive query (100 rows). Check the 3D dashboard.";
    }
}
