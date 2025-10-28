package com.example.db_log.p6spy;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class SqlLogCapture extends AppenderBase<ILoggingEvent> {

    private static final ThreadLocal<String> lastQuery = new ThreadLocal<>();

    public static String getLastQuery() {
        String sql = lastQuery.get();
        lastQuery.remove(); // Clear after reading
        return sql;
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        String sql = eventObject.getFormattedMessage();
        if (sql != null && !sql.trim().isEmpty()) {
            // Replace all consecutive whitespace characters (including newlines) with a single space
            String singleLineSql = sql.replaceAll("\\s+", " ").trim();
            lastQuery.set(singleLineSql);
        }
    }
}
