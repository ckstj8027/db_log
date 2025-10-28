package com.example.db_log.aop;

import com.example.db_log.domain.QueryLog;
import com.example.db_log.p6spy.SqlLogCapture;
import com.example.db_log.repository.QueryLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;

@Aspect
@Component
@RequiredArgsConstructor
public class QueryLoggingAspect {

    private final QueryLogRepository queryLogRepository;

    @Around("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..)) && !target(com.example.db_log.repository.QueryLogRepository)")
    public Object logQuery(ProceedingJoinPoint joinPoint) throws Throwable {

        // Check if the query is triggered by a user's web request.
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            // If not, it's a startup query (e.g., from @PostConstruct). Just execute it without logging.
            return joinPoint.proceed();
        }

        // --- The rest of the logic will only run for web requests --- 

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        String actualSql = SqlLogCapture.getLastQuery();
        if (actualSql == null || actualSql.trim().isEmpty()) {
            actualSql = joinPoint.getSignature().toShortString(); // Fallback
        }

        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        String clientIp = request.getRemoteAddr();

        long executionTime = endTime - startTime;

        int resultRowCount = 0;
        if (result instanceof Collection) {
            resultRowCount = ((Collection<?>) result).size();
        } else if (result != null) {
            resultRowCount = 1;
        }

        QueryLog queryLog = new QueryLog(sessionId, clientIp, actualSql, executionTime, resultRowCount);
        queryLogRepository.save(queryLog);

        return result;
    }
}
